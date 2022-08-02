package com.semihtumay.bankingproject.repositories;

import com.semihtumay.bankingproject.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByAccountNumberIs(Integer accountNumber);

    Optional<Account> findById(Long id);

    Optional<Account> findByUsers_Id(Long id);







}