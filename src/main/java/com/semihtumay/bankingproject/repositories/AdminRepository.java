package com.semihtumay.bankingproject.repositories;

import com.semihtumay.bankingproject.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByIdNumber(String idNumber);

    Optional<Admin> findByEmailEqualsIgnoreCase(String email);

}