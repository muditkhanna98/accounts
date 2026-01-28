package com.mudit.accounts.services;

import com.mudit.accounts.dtos.CustomerDto;
import com.mudit.accounts.mappers.AccountsStructMapper;
import com.mudit.accounts.repositories.AccountsRepository;
import com.mudit.accounts.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountsService {
    private final AccountsRepository accountsRepository;
    private final CustomerRepository customerRepository;
    private final AccountsStructMapper accountsMapper;

    @Override
    public void createAccount(CustomerDto customerDto) {

    }
}
