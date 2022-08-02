package com.semihtumay.bankingproject.repositories;

import com.semihtumay.bankingproject.entities.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transactions, Long> {
}