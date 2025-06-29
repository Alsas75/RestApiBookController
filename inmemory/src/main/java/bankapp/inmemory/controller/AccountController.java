package bankapp.inmemory.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AccountController {

    @GetMapping("/public/info")
    public String publicInfo() {
        return "Public endpoint. Welcome to the bank app.";
    }

    @GetMapping("/accounts")
    public String getAccountsList() {
        return "List of accounts";
    }

    @PostMapping("/accounts")
    @PreAuthorize("hasRole('ADMIN')")
    public String createAccount() {
        return "Account created";
    }

}
