package by.tms.tmsc35p3.controller;

import by.tms.tmsc35p3.dto.LoginRequest;
import by.tms.tmsc35p3.dto.RegisterRequest;
import by.tms.tmsc35p3.entity.User;
import by.tms.tmsc35p3.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import by.tms.tmsc35p3.service.AuthService;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        authService.register(request);
        return ResponseEntity.ok(Map.of("message", "Пользователь успешно зарегистрирован"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        User user = authService.login(request);
        String token = jwtService.generateToken(user);
        return ResponseEntity.ok(Map.of(
                "token", token,
                "email", user.getEmail()
        ));
    }
}
