package com.semihtumay.bankingproject.restcontrollers;

import com.semihtumay.bankingproject.entities.Users;
import com.semihtumay.bankingproject.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Validated
@RequestMapping("/user")
public class UserController {
    final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/application")
    public ResponseEntity application(@Valid @RequestBody Users user){
        return userService.application(user);
    }

    @PostMapping("/register")
    public ResponseEntity register(@Valid @RequestParam String idNumber){
        return userService.userRegister(idNumber);
    }
}
