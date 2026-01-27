package com.mudit.accounts.repositories;

import com.mudit.accounts.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}