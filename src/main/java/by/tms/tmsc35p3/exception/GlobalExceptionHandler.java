package by.tms.tmsc35p3.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Map<String, String>>> handleException(Exception ex) {
        Problem problem = createProblemFromException(ex);
        return buildErrorResponse(problem);
    }

    public static ResponseEntity<Map<String, Map<String, String>>> createErrorResponse(String code, String message) {
        return createErrorResponse(code, message, getHttpStatusFromCode(code));
    }

    public static ResponseEntity<Map<String, Map<String, String>>> createErrorResponse(String code, String message, HttpStatus status) {
        Map<String, String> error = new HashMap<>();
        error.put("code", code);
        error.put("message", message);

        Map<String, Map<String, String>> response = new HashMap<>();
        response.put("error", error);

        return ResponseEntity.status(status).body(response);
    }

    private Problem createProblemFromException(Exception ex) {

        if (ex instanceof org.zalando.problem.ThrowableProblem throwableProblem) {
            return throwableProblem;
        }

        if (ex instanceof MethodArgumentNotValidException validationEx) {
            StringBuilder message = new StringBuilder();
            validationEx.getBindingResult().getFieldErrors().forEach(error -> {
                if (!message.isEmpty()) {
                    message.append("; ");
                }
                message.append(error.getDefaultMessage());
            });
            return Problem.builder()
                    .withStatus(Status.BAD_REQUEST)
                    .withDetail(message.toString())
                    .build();
        }

        if (ex instanceof PostNotFoundException) {
            return Problem.builder()
                    .withStatus(Status.NOT_FOUND)
                    .withDetail(ex.getMessage())
                    .build();
        }

        String message = ex.getMessage();
        Status status = Status.INTERNAL_SERVER_ERROR;

        if (message != null) {
            if (message.contains("зарегистрирован") || message.contains("Email")) {
                status = Status.BAD_REQUEST;
            } else if (message.contains("пароль") || message.contains("email") || message.contains("Неверный") ||
                       message.contains("токен") || ex.getClass().getSimpleName().contains("Jwt")) {
                status = Status.UNAUTHORIZED;
            } else if (ex.getClass().getSimpleName().contains("NotFound")) {
                status = Status.NOT_FOUND;
            }
        }

        return Problem.builder()
                .withStatus(status)
                .withDetail(message != null ? message : "Внутренняя ошибка сервера")
                .build();
    }

    private ResponseEntity<Map<String, Map<String, String>>> buildErrorResponse(Problem problem) {
        int statusCode = Objects.requireNonNull(problem.getStatus()).getStatusCode();
        String code = mapStatusCodeToCode(statusCode);
        String message = problem.getDetail() != null ? problem.getDetail() : problem.getTitle();
        HttpStatus httpStatus = HttpStatus.valueOf(statusCode);

        return createErrorResponse(code, message, httpStatus);
    }

    private String mapStatusCodeToCode(int statusCode) {
        return switch (statusCode) {
            case 404 -> "NOT_FOUND";
            case 400 -> "BAD_REQUEST";
            case 401 -> "UNAUTHORIZED";
            case 403 -> "FORBIDDEN";
            case 500 -> "INTERNAL_SERVER_ERROR";
            default -> String.valueOf(statusCode);
        };
    }

    private static HttpStatus getHttpStatusFromCode(String code) {
        return switch (code.toUpperCase()) {
            case "NOT_FOUND" -> HttpStatus.NOT_FOUND;
            case "BAD_REQUEST" -> HttpStatus.BAD_REQUEST;
            case "UNAUTHORIZED" -> HttpStatus.UNAUTHORIZED;
            case "FORBIDDEN" -> HttpStatus.FORBIDDEN;
            case "INTERNAL_SERVER_ERROR" -> HttpStatus.INTERNAL_SERVER_ERROR;
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
    }
}
