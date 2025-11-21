package by.tms.tmsc35p3.controller;


import by.tms.tmsc35p3.dto.AccountDto;
import by.tms.tmsc35p3.dto.UpdatePasswordRequest;
import by.tms.tmsc35p3.entity.Account;
import by.tms.tmsc35p3.exception.GlobalExceptionHandler;
import by.tms.tmsc35p3.exception.IncorrectOldPassword;
import by.tms.tmsc35p3.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth/account")
@RequiredArgsConstructor
public class AccountController {


    private final UserService userService;

    @PostMapping
    public ResponseEntity<Account> create(@RequestBody AccountDto dto) {

        Account account = new Account();
        account.setUsername(dto.getUsername());
        account.setEmail(dto.getEmail());
        account.setPassword(dto.getPassword());

        Account save = userService.create(account);

        return ResponseEntity.ok(save);

    }

    @PatchMapping("/update-password")
    public ResponseEntity<?> updatePassword(@RequestBody UpdatePasswordRequest updatePasswordRequest,
                                            @RequestBody Long id) {
        // Когда сделают jwt, Отредачить получение id <<<---
        try {
            Account account = userService.updatePassword(id, updatePasswordRequest);
            return ResponseEntity.ok(account);
        } catch (IncorrectOldPassword e) {
            return GlobalExceptionHandler.createErrorResponse("400", e.getMessage());
        }
    }


}

