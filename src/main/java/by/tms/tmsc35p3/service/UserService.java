package by.tms.tmsc35p3.service;

import by.tms.tmsc35p3.dto.UpdatePasswordRequest;
import by.tms.tmsc35p3.entity.Role;
import by.tms.tmsc35p3.entity.Account;
import by.tms.tmsc35p3.exception.IncorrectOldPassword;
import by.tms.tmsc35p3.repository.AccountRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private AccountRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public Account create(Account user) {
        user.setRoles(Set.of(Role.ROLE_USER));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Account findById(Long id) {
        Optional<Account> user = userRepository.findById(id);

        return user.orElseThrow(() ->
                new EntityNotFoundException("Account not found"));
    }

    public Account updatePassword(Long id, UpdatePasswordRequest updatePasswordRequest) {
        Account account = findById(id);
        if(!bCryptPasswordEncoder.matches(updatePasswordRequest.getOldPassword(), account.getPassword())){
            throw new IncorrectOldPassword("Неверный текущий пароль");
        }
        account.setPassword(bCryptPasswordEncoder.encode(updatePasswordRequest.getNewPassword()));
        return userRepository.save(account);
    }
}
