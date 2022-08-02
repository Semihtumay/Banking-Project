package com.semihtumay.bankingproject.services;

import com.semihtumay.bankingproject.entities.Account;
import com.semihtumay.bankingproject.entities.Transactions;
import com.semihtumay.bankingproject.entities.Users;
import com.semihtumay.bankingproject.repositories.AccountRepository;
import com.semihtumay.bankingproject.repositories.TransactionRepository;
import com.semihtumay.bankingproject.utils.REnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Optional;

@Service
public class TransactionService {

    final TransactionRepository  transactionRepository;
    final AccountRepository accountRepository;
    final HttpSession httpSession;

    public TransactionService(TransactionRepository transactionRepository, AccountRepository accountRepository, HttpSession httpSession) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.httpSession = httpSession;
    }

    public ResponseEntity deposit(Transactions transactions) {
        HashMap<REnum, Object> hm = new LinkedHashMap<>();
        Users users = (Users) httpSession.getAttribute("users");
        Optional<Account> optionalAccount = accountRepository.findByUsers_Id(users.getId());
        if (optionalAccount.isPresent()) {
            transactions.setAmount(transactions.getAmount());
            transactions.setDescription("Deposit");
            transactions.setType("Deposit");
            Double newBalance = optionalAccount.get().getCurrentBalance() + transactions.getAmount();
            optionalAccount.get().setCurrentBalance(newBalance);
            transactions.setDate(new Date());
            transactions.setAccount(optionalAccount.get());
            transactionRepository.save(transactions);
            hm.put(REnum.status,true);
            hm.put(REnum.result,transactions);
            return new ResponseEntity<>(hm, HttpStatus.OK);
        }else {
            hm.put(REnum.status, false);
            hm.put(REnum.message,"Invalid operation");
            return new ResponseEntity( hm, HttpStatus.NOT_ACCEPTABLE );
        }
    }

    public ResponseEntity withdraw(Transactions transactions) {
        HashMap<REnum, Object> hm = new LinkedHashMap<>();
        Users users = (Users) httpSession.getAttribute("users");
        Optional<Account> optionalAccount = accountRepository.findByUsers_Id(users.getId());
        if (optionalAccount.isPresent()) {
            if (optionalAccount.get().getCurrentBalance() > transactions.getAmount()) {
            transactions.setAmount(transactions.getAmount());
            transactions.setDescription("Withdraw Money");
            transactions.setType("Withdraw");
            Double newBalance = optionalAccount.get().getCurrentBalance() - transactions.getAmount();
            optionalAccount.get().setCurrentBalance(newBalance);
            transactions.setDate(new Date());
            transactions.setAccount(optionalAccount.get());
            transactionRepository.save(transactions);
            hm.put(REnum.status,true);
            hm.put(REnum.result,transactions);
            return new ResponseEntity<>(hm, HttpStatus.OK);
            }else {
            hm.put(REnum.status, false);
            hm.put(REnum.message,"Insufficient balance");
            return new ResponseEntity( hm, HttpStatus.NOT_ACCEPTABLE );
            }
        }else {
            hm.put(REnum.status, false);
            hm.put(REnum.message,"Invalid operation");
            return new ResponseEntity( hm, HttpStatus.NOT_ACCEPTABLE );
        }
    }

    public ResponseEntity transfer(Integer accountNumber, Double amount, String description) {
        HashMap<REnum, Object> hm = new LinkedHashMap<>();
        Users users = (Users) httpSession.getAttribute("users");
        Optional<Account> optionalAccount = accountRepository.findByUsers_Id(users.getId());
        Optional<Account> optional = accountRepository.findByAccountNumberIs(accountNumber);
        if (optionalAccount.isPresent() && optional.isPresent()) {
            if (optionalAccount.get().getCurrentBalance() > amount) {
                Double newBalance = optionalAccount.get().getCurrentBalance() - amount;
                optionalAccount.get().setCurrentBalance(newBalance);
                Transactions newTransaction = new Transactions();
                newTransaction.setAccount(optionalAccount.get());
                newTransaction.setDescription(description);
                newTransaction.setAmount(amount);
                newTransaction.setDate(new Date());
                newTransaction.setType("Transfer");
                Double balance = optional.get().getCurrentBalance() + amount;
                optional.get().setCurrentBalance(balance);
                transactionRepository.save(newTransaction);
                hm.put(REnum.status,true);
                hm.put(REnum.result,newTransaction);
                return new ResponseEntity<>(hm, HttpStatus.OK);
            }else {
                hm.put(REnum.status, false);
                hm.put(REnum.message,"Insufficient balance");
                return new ResponseEntity( hm, HttpStatus.NOT_ACCEPTABLE );
            }

        }else {
        hm.put(REnum.status, false);
        hm.put(REnum.message,"Invalid operation");
        return new ResponseEntity( hm, HttpStatus.NOT_ACCEPTABLE );
        }
    }
}
