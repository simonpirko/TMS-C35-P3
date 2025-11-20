package by.tms.tmsc35p3.controller;

import by.tms.tmsc35p3.dto.LoginRequest;
import by.tms.tmsc35p3.entity.Account;
import by.tms.tmsc35p3.repository.AccountRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
@PropertySource("classpath:application.properties")
public class AuthController {
    @Value("${spring.security.jwt.secret}")
    private String secret;

    @Autowired
    private AccountRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        Optional<Account> byUsername = userRepository.findByUsername(request.getUsername());

        if (byUsername.isEmpty()){

            return ResponseEntity.badRequest().build();
        }
        Account account = byUsername.get();
        String password = account.getPassword();
        boolean matches = bCryptPasswordEncoder.matches(request.getPassword(), password);

        if (matches){
            SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes());
            String jws = Jwts
                    .builder()
                    .setSubject(account.getUsername())
                    .addClaims(Map.of("name", account.getUsername()))
                    .addClaims(Map.of("role", account.getRoles().iterator().next().name()))
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                    .signWith(secretKey, SignatureAlgorithm.HS256)
                    .compact();
            return ResponseEntity.ok(jws);

        }
        return ResponseEntity.badRequest().build();
    }
}
