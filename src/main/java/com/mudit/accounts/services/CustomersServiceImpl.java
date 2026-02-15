package com.mudit.accounts.services;

import com.mudit.accounts.dtos.AccountsDto;
import com.mudit.accounts.dtos.CardsDto;
import com.mudit.accounts.dtos.CustomerDetailsDto;
import com.mudit.accounts.dtos.LoansDto;
import com.mudit.accounts.entities.Accounts;
import com.mudit.accounts.entities.Customer;
import com.mudit.accounts.exceptions.ResourceNotFoundException;
import com.mudit.accounts.mappers.AccountsMapper;
import com.mudit.accounts.mappers.CustomerMapper;
import com.mudit.accounts.repositories.AccountsRepository;
import com.mudit.accounts.repositories.CustomerRepository;
import com.mudit.accounts.services.client.CardsFeignClient;
import com.mudit.accounts.services.client.LoansFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomersServiceImpl implements ICustomersService {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;
    private CardsFeignClient cardsFeignClient;
    private LoansFeignClient loansFeignClient;

    /**
     * @param mobileNumber - Input Mobile Number
     * @return Customer Details based on a given mobileNumber
     */
    @Override
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer, new CustomerDetailsDto());
        customerDetailsDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));

        ResponseEntity<LoansDto> loansDtoResponseEntity = loansFeignClient.fetchLoanDetails(mobileNumber);
        customerDetailsDto.setLoansDto(loansDtoResponseEntity.getBody());

        ResponseEntity<CardsDto> cardsDtoResponseEntity = cardsFeignClient.fetchCardDetails(mobileNumber);
        customerDetailsDto.setCardsDto(cardsDtoResponseEntity.getBody());

        return customerDetailsDto;

    }
}
