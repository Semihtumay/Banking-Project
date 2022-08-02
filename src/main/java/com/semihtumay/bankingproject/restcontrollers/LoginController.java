package com.semihtumay.bankingproject.restcontrollers;

import com.semihtumay.bankingproject.entities.Login;
import com.semihtumay.bankingproject.services.LoginService;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class LoginController {
    final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }
    @PostMapping("/login")
    public ResponseEntity auth(@RequestBody Login login) {
        return loginService.auth(login);
    }
}
