package com.semihtumay.bankingproject.services;

import com.semihtumay.bankingproject.entities.Admin;
import com.semihtumay.bankingproject.entities.Users;
import com.semihtumay.bankingproject.repositories.AdminRepository;
import com.semihtumay.bankingproject.utils.REnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Optional;

@Service
public class AdminService {
    final AdminRepository adminRepositoy;
    final PasswordEncoder passwordEncoder;

    public AdminService(AdminRepository adminRepositoy, PasswordEncoder passwordEncoder) {
        this.adminRepositoy = adminRepositoy;
        this.passwordEncoder = passwordEncoder;
    }
    public ResponseEntity register(Admin admin) {
        HashMap<REnum, Object> hm = new LinkedHashMap<>();
        Optional<Admin> optionalAdmin = adminRepositoy.findByIdNumber(admin.getIdNumber());
        if(optionalAdmin.isPresent()) {
            hm.put(REnum.status, false);
            hm.put(REnum.message,"This user have already registered");
            return new ResponseEntity( hm, HttpStatus.NOT_ACCEPTABLE );
        }else {
            admin.setFirstname(admin.getFirstname());
            admin.setSurname(admin.getSurname());
            admin.setEmail(admin.getEmail());
            admin.setIdNumber(admin.getIdNumber());
            admin.setPassword(passwordEncoder.encode(admin.getPassword()));
            admin.setPhone(admin.getPhone());
            Admin newAdmin = adminRepositoy.save(admin);
            hm.put(REnum.status,true);
            hm.put(REnum.result,newAdmin);
            return new ResponseEntity<>(hm,HttpStatus.OK);
        }
    }
}
