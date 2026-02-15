package com.mudit.accounts.services;

import com.mudit.accounts.dtos.CustomerDetailsDto;

public interface ICustomersService {
    CustomerDetailsDto fetchCustomerDetails(String mobileNumber);
}
