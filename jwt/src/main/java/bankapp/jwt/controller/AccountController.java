package bankapp.jwt.controller;

import bankapp.jwt.utils.JwtUtils;
import bankapp.jwt.model.BankAuthRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Collections;

@RestController
public class AccountController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;



    @GetMapping("/api/public/rates")
    public String getRates() {
        return "This endpoint is only accessible for all.";
    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody BankAuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

        String token = jwtUtils.generateToken(authRequest.getUsername());

        return ResponseEntity.ok(Collections.singletonMap("token",token));
    }

    @GetMapping("/api/accounts/me")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<?> getCurrentUser() {
        return ResponseEntity.ok().body("This endpoint is only accessible for users with ADMIN or USER role.");
    }


}