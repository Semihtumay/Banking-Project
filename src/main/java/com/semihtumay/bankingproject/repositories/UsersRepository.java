package com.semihtumay.bankingproject.repositories;

import com.semihtumay.bankingproject.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByIdNumber(String idNumber);

}