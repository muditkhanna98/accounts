package com.mudit.accounts.services;

import com.mudit.accounts.constants.AccountsConstants;
import com.mudit.accounts.dtos.AccountsDto;
import com.mudit.accounts.dtos.AccountsMsgDto;
import com.mudit.accounts.dtos.CustomerDto;
import com.mudit.accounts.entities.Accounts;
import com.mudit.accounts.entities.Customer;
import com.mudit.accounts.exceptions.CustomerAlreadyExistsException;
import com.mudit.accounts.exceptions.ResourceNotFoundException;
import com.mudit.accounts.mappers.AccountsStructMapper;
import com.mudit.accounts.mappers.CustomerStructMapper;
import com.mudit.accounts.repositories.AccountsRepository;
import com.mudit.accounts.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountsService {
    private final AccountsRepository accountsRepository;
    private final CustomerRepository customerRepository;
    private final AccountsStructMapper accountsStructMapper;
    private final CustomerStructMapper customerStructMapper;
    private static final Logger log = LoggerFactory.getLogger(AccountsServiceImpl.class);
    private final StreamBridge streamBridge;

    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = customerRepository.findByMobileNumber(customerDto.getMobileNumber()).orElse(null);
        if (customer != null) {
            throw new CustomerAlreadyExistsException("Customer already exists");
        } else {
            Customer customerEntity = customerStructMapper.toCustomerEntity(customerDto);
            customerEntity.setCreatedAt(LocalDateTime.now());
            customerEntity.setUpdatedAt(LocalDateTime.now());
            customerEntity.setCreatedBy(customerDto.getName());
            Customer savedCustomer = customerRepository.save(customerEntity);
            Accounts account = createNewAccount(savedCustomer);

            accountsRepository.save(account);
            sendCommunication(account, savedCustomer);
        }
    }

    private void sendCommunication(Accounts account, Customer customer) {
        var accountsMsgDto = new AccountsMsgDto(account.getAccountNumber(), customer.getName(),
                customer.getEmail(), customer.getMobileNumber());
        log.info("Sending Communication request for the details: {}", accountsMsgDto);
        var result = streamBridge.send("sendCommunication-out-0", accountsMsgDto);
        log.info("Is the Communication request successfully triggered ? : {}", result);
    }

    @Override
    public CustomerDto fetchAccountDetails(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElse(null);
        if (customer == null) {
            throw new ResourceNotFoundException("Customer not found");
        } else {
            Accounts account = accountsRepository.findByCustomerId(customer.getCustomerId()).orElse(null);
            if (account == null) {
                throw new ResourceNotFoundException("Account not found");
            }

            AccountsDto accountsDto = accountsStructMapper.toAccountsDto(account);
            return customerStructMapper.toCustomerDto(customer, accountsDto);
        }
    }

    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;
        AccountsDto accountsDto = customerDto.getAccountsDto();

        if (accountsDto != null) {
            Accounts accounts = accountsRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Account not found")
            );
            accountsStructMapper.update(accountsDto, accounts);
            accounts = accountsRepository.save(accounts);

            Long customerId = accounts.getCustomerId();
            Customer customer = customerRepository.findById(customerId).orElseThrow(
                    () -> new ResourceNotFoundException("Customer not found")
            );
            customerStructMapper.update(customerDto, customer);
            customerRepository.save(customer);
            isUpdated = true;
        }
        return isUpdated;
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer not found")
        );
        accountsRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return true;
    }

    private Accounts createNewAccount(Customer customer) {
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);
        return newAccount;
    }
}
