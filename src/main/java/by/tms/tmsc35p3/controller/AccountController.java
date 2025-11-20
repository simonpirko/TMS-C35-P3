package by.tms.tmsc35p3.controller;


import by.tms.tmsc35p3.dto.AccountDto;
import by.tms.tmsc35p3.entity.Account;
import by.tms.tmsc35p3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth/account")
public class AccountController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Account> create(@RequestBody AccountDto dto) {

        Account account = new Account();
        account.setUsername(dto.getUsername());
        account.setEmail(dto.getEmail());
        account.setPassword(dto.getPassword());

        Account save = userService.create(account);

        return ResponseEntity.ok(save);

    }
}

