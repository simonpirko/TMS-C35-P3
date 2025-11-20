package by.tms.tmsc35p3.service;

import by.tms.tmsc35p3.dto.UpdatePasswordRequest;
import by.tms.tmsc35p3.entity.User;
import by.tms.tmsc35p3.exception.IncorrectOldPassword;
import by.tms.tmsc35p3.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void updatePassword(String email, UpdatePasswordRequest request) {
        Optional<User> user = userRepository.findByEmail(email);

        if(!passwordEncoder.matches(request.getOldPassword(), user.get().getPassword())) {
            throw new IncorrectOldPassword("Неверный существующий пароль");
        }
        user.get().setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user.get());

    }

    public boolean existsById(Long id){
        return userRepository.existsById(id);
    }

}
