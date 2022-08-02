package com.semihtumay.bankingproject.restcontrollers;

import com.semihtumay.bankingproject.entities.Transactions;
import com.semihtumay.bankingproject.entities.Users;
import com.semihtumay.bankingproject.services.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class TransactionController {
    final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }
    @PostMapping("/withdraw")
    public ResponseEntity withdraw(@Valid @RequestBody Transactions transactions){
        return transactionService.withdraw(transactions);
    }
    @PostMapping("/deposit")
    public ResponseEntity deposit(@Valid @RequestBody Transactions transactions){
        return transactionService.deposit(transactions);
    }
    @PostMapping("/transfer")
    public ResponseEntity transfer(@Valid @RequestParam Integer accountNumber, Double amount, String description){
        return transactionService.transfer(accountNumber, amount, description);
    }
}
