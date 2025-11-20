package by.tms.tmsc35p3.controller;

import by.tms.tmsc35p3.entity.Account;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth/greeting")
public class GreetingController {

    @GetMapping
    public String hello(@AuthenticationPrincipal Account account) {
        return "Hello " + account.getUsername() + "!";
    }
}
