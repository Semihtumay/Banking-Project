package com.semihtumay.bankingproject.services;

import com.semihtumay.bankingproject.entities.Account;
import com.semihtumay.bankingproject.entities.Role;
import com.semihtumay.bankingproject.entities.Users;
import com.semihtumay.bankingproject.repositories.AccountRepository;
import com.semihtumay.bankingproject.repositories.UsersRepository;
import com.semihtumay.bankingproject.utils.REnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    final UsersRepository usersRepository;
    final PasswordEncoder passwordEncoder;
    final AccountRepository accountRepository;

    public UserService(UsersRepository usersRepository, PasswordEncoder passwordEncoder, AccountRepository accountRepository) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.accountRepository = accountRepository;
    }
    public ResponseEntity application(Users user) {
        HashMap<REnum, Object> hm = new LinkedHashMap<>();
        Optional<Users> optionalUsers = usersRepository.findByIdNumber(user.getIdNumber());
        if(optionalUsers.isPresent()) {
            hm.put(REnum.status, false);
            hm.put(REnum.message,"This user have already registered");
            return new ResponseEntity( hm, HttpStatus.NOT_ACCEPTABLE );
        }else {
            user.setFirstname(user.getFirstname());
            user.setSurname(user.getSurname());
            user.setEmail(user.getEmail());
            user.setIdNumber(user.getIdNumber());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setPhone(user.getPhone());
            List<Role> roles=new ArrayList<>();
            Role role=new Role();
            role.setId(2);
            role.setName("ROLE_user");
            roles.add(role);
            user.setRoles(roles);
            Users newUser = usersRepository.save(user);
            hm.put(REnum.status,true);
            hm.put(REnum.result,newUser);
            return new ResponseEntity<>(hm,HttpStatus.OK);
        }
    }

    public ResponseEntity userRegister(String idNumber) {
        HashMap<REnum, Object> hm = new LinkedHashMap<>();
        Optional<Users> optionalUsers = usersRepository.findByIdNumber(idNumber);
        if (optionalUsers.isPresent()) {
            Users users = optionalUsers.get();
            users.setEnabled(true);
            users.setTokenExpired(true);
            users.setAccounts(accounts(users));
            Users newUser = usersRepository.saveAndFlush(users);
            hm.put(REnum.status,true);
            hm.put(REnum.result,newUser);
            return new ResponseEntity<>(hm,HttpStatus.OK);
        }else {
            hm.put(REnum.status, false);
            hm.put(REnum.message,"There is no application for this ID number.");
            return new ResponseEntity( hm, HttpStatus.NOT_ACCEPTABLE );
        }
    }

    public List<Account> accounts(Users users) {
        List<Account> accounts = new ArrayList<>();
        Account account = new Account();
        account.setBankName("YapıKredi");
        account.setCurrentBalance(0.0);
        account.setUsers(users);
        Random random = new Random();
        int number = 100000000 + random.nextInt(1000000000);
        Optional<Account> optionalAccount = accountRepository.findByAccountNumberIs(number);
        if (optionalAccount.isPresent()) {
            int number2 = number + 1;
            account.setAccountNumber(number2);
        }else {
            account.setAccountNumber(number);
        }
        accounts.add(account);
        return accounts;
    }
}
