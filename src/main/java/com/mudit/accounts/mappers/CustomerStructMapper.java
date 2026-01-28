package com.mudit.accounts.mappers;

import com.mudit.accounts.dtos.AccountsDto;
import com.mudit.accounts.dtos.CustomerDto;
import com.mudit.accounts.entities.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CustomerStructMapper {
    Customer toCustomerEntity(CustomerDto customerDto);

    CustomerDto toCustomerDto(Customer customer);

    CustomerDto toCustomerDto(Customer customer, AccountsDto accountsDto);

    void update(CustomerDto customerDto, @MappingTarget Customer customer);
}
