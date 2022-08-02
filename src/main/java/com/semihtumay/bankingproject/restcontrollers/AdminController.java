package com.semihtumay.bankingproject.restcontrollers;

import com.semihtumay.bankingproject.entities.Admin;
import com.semihtumay.bankingproject.services.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin")
@Validated
public class AdminController {
    final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/register")
    public ResponseEntity register(@Valid @RequestBody Admin admin) {
        return adminService.register(admin);
    }
}
