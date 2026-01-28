package com.mudit.accounts.mappers;

import com.mudit.accounts.dtos.CustomerDto;
import com.mudit.accounts.entities.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerStructMapper {
    Customer toCustomerEntity(CustomerDto customerDto);

    CustomerDto toCustomerDto(Customer customer);
}
