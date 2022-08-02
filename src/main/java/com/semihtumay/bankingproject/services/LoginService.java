package com.semihtumay.bankingproject.services;

import com.semihtumay.bankingproject.entities.Admin;
import com.semihtumay.bankingproject.entities.Login;
import com.semihtumay.bankingproject.entities.Users;
import com.semihtumay.bankingproject.configs.JwtUtil;
import com.semihtumay.bankingproject.utils.REnum;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class LoginService {
    final AuthenticationManager authenticationManager;
    final JwtUtil jwtUtil;
    final LoginUserDetailService loginUserDetailService;
    final HttpSession httpSession;

    public LoginService(AuthenticationManager authenticationManager, JwtUtil jwtUtil, LoginUserDetailService loginUserDetailService, HttpSession httpSession) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.loginUserDetailService = loginUserDetailService;
        this.httpSession = httpSession;
    }
    public ResponseEntity auth(Login login) {
        Map<REnum, Object> hm = new LinkedHashMap<>();
        try {

            authenticationManager.authenticate( new UsernamePasswordAuthenticationToken(
                    login.getUsername(), login.getPassword()
            ) );

            UserDetails userDetails = loginUserDetailService.loadUserByUsername(login.getUsername());
            Admin admin = (Admin) httpSession.getAttribute("admin");
            Users users = (Users) httpSession.getAttribute("users");

            String jwt = jwtUtil.generateToken(userDetails);
            hm.put(REnum.status, true);
            if(admin !=null){
                hm.put(REnum.result,admin);
            }
            else {
                hm.put(REnum.result,users);
            }
            hm.put( REnum.jwt, jwt );

            return new ResponseEntity(hm, HttpStatus.OK);
        }catch (Exception ex) {
            hm.put(REnum.status, false);
            hm.put( REnum.error, ex.getMessage() );
            hm.put(REnum.message,"No registered person found");
            return new ResponseEntity(hm, HttpStatus.BAD_REQUEST);
        }

    }
}
