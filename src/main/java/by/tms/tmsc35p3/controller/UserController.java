package by.tms.tmsc35p3.controller;

import by.tms.tmsc35p3.entity.User;
import by.tms.tmsc35p3.jwt.JwtService;
import by.tms.tmsc35p3.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final JwtService jwtService;
    private final UserRepository userRepository;

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
}