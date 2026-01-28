package com.mudit.accounts.services;

import com.mudit.accounts.dtos.CustomerDto;

public interface IAccountsService {
    void createAccount(CustomerDto customerDto);

    CustomerDto fetchAccountDetails(String mobileNumber);
}
