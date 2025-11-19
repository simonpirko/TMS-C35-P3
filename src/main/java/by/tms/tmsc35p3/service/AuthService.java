package by.tms.tmsc35p3.service;

import by.tms.tmsc35p3.dto.LoginRequest;
import by.tms.tmsc35p3.entity.Account;
import by.tms.tmsc35p3.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public Account login(LoginRequest request) {
        return accountRepository.findByUsername(request.getUsername())
                .filter(u -> passwordEncoder.matches(request.getPassword(), u.getPassword()))
                .orElseThrow(() -> new RuntimeException("Неверный email или пароль"));
    }
}
