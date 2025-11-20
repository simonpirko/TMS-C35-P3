package by.tms.tmsc35p3.controller;

import by.tms.tmsc35p3.dto.UpdatePasswordRequest;
import by.tms.tmsc35p3.entity.User;
import by.tms.tmsc35p3.exception.IncorrectOldPassword;
import by.tms.tmsc35p3.jwt.JwtService;
import by.tms.tmsc35p3.repository.UserRepository;
import by.tms.tmsc35p3.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(@RequestHeader("Authorization") String authHeader) {
        // Проверяем, что хедер начинается с "Bearer "
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("Отсутствует токен авторизации");
        }
        // Извлекаем сам токен
        String token = authHeader.substring(7);
        // Проверяем токен
        if (!jwtService.validateToken(token)) {
            return ResponseEntity.status(401).body("Невалидный токен");
        }
        // Извлекаем email из токена
        String email = jwtService.extractUsername(token);
        // Ищем пользователя по email
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            return ResponseEntity.status(404).body("Пользователь не найден");
        }

        return ResponseEntity.ok(user.get());
    }

    @PatchMapping("/change-password")
    public ResponseEntity<?> changePassword(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody UpdatePasswordRequest request) {

        // Проверяем, что хедер начинается с "Bearer "
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("Отсутствует токен авторизации");
        }
        // Извлекаем сам токен
        String token = authHeader.substring(7);
        // Проверяем токен
        if (!jwtService.validateToken(token)) {
            return ResponseEntity.status(401).body("Невалидный токен");
        }
        // Извлекаем email из токена
        String email = jwtService.extractUsername(token);
        // Ищем пользователя по email
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            return ResponseEntity.status(404).body("Пользователь не найден");
        }
        try {
            userService.updatePassword(email, request);
        } catch (IncorrectOldPassword e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }


        return ResponseEntity.ok(user.get());
    }
}
