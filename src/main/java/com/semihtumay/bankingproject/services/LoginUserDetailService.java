package com.semihtumay.bankingproject.services;

import com.semihtumay.bankingproject.entities.Admin;
import com.semihtumay.bankingproject.entities.Role;
import com.semihtumay.bankingproject.entities.Users;
import com.semihtumay.bankingproject.repositories.AdminRepository;
import com.semihtumay.bankingproject.repositories.UsersRepository;
import com.semihtumay.bankingproject.configs.JwtUtil;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class LoginUserDetailService implements UserDetailsService {
    final JwtUtil jwtUtil;
    final AuthenticationManager authenticationManager;
    final UsersRepository usersRepository;
    final HttpSession httpSession;
    final AdminRepository adminRepository;

    public LoginUserDetailService(JwtUtil jwtUtil, @Lazy AuthenticationManager authenticationManager, UsersRepository usersRepository, HttpSession httpSession, AdminRepository adminRepository) {
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.usersRepository = usersRepository;
        this.httpSession = httpSession;
        this.adminRepository = adminRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> optionalUsers = usersRepository.findByIdNumber(username);
        Optional<Admin> optionalAdmin = adminRepository.findByEmailEqualsIgnoreCase((username));
        if (optionalUsers.isPresent()) {
            Users users = optionalUsers.get();
            UserDetails userDetails = new User(
                    users.getIdNumber(),
                    users.getPassword(),
                    users.isEnabled(),
                    users.isTokenExpired(),
                    true,
                    true,
                    roles(users.getRoles())
            );
            httpSession.setAttribute("users",users);
            return userDetails;
        }else if (optionalAdmin.isPresent()) {
            Admin admin=optionalAdmin.get();
            UserDetails userDetails = new User(
                    admin.getEmail(),
                    admin.getPassword(),
                    admin.isEnabled(),
                    admin.isTokenExpired(),
                    true,
                    true,
                    roles(admin.getRoles())

            );
            httpSession.setAttribute("admin",admin);
            return userDetails;
        }else {throw new UsernameNotFoundException("There is no such user. ");}
    }

    public Collection roles(List<Role> roles ) {
        List<GrantedAuthority> ls = new ArrayList<>();
        for ( Role role : roles ) {
            ls.add( new SimpleGrantedAuthority( role.getName() ));
        }
        System.out.println(ls);
        return ls;

    }
}
