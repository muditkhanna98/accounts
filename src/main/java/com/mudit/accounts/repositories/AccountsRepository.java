package com.mudit.accounts.repositories;

import com.mudit.accounts.entities.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountsRepository extends JpaRepository<Accounts, Long> {
}