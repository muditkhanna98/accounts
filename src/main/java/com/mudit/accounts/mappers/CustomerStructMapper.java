package com.mudit.accounts.mappers;

import com.mudit.accounts.dtos.AccountsDto;
import com.mudit.accounts.dtos.CustomerDto;
import com.mudit.accounts.entities.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerStructMapper {
    Customer toCustomerEntity(CustomerDto customerDto);

    CustomerDto toCustomerDto(Customer customer);

    // Combine customer + accounts into response
    CustomerDto toCustomerDto(Customer customer, AccountsDto accountsDto);
}
