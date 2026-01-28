package com.mudit.accounts.repositories;

import com.mudit.accounts.entities.Accounts;
import com.mudit.accounts.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountsRepository extends JpaRepository<Accounts, Long> {
    Optional<Accounts> findByCustomerId(Long customerId);
}