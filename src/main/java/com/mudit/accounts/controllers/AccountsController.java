package com.mudit.accounts.controllers;

import com.mudit.accounts.constants.AccountsConstants;
import com.mudit.accounts.dtos.CustomerDto;
import com.mudit.accounts.dtos.ResponseDto;
import com.mudit.accounts.services.AccountsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class AccountsController {
    private final AccountsServiceImpl accountsServiceImpl;

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@RequestBody CustomerDto customerDto) {
        accountsServiceImpl.createAccount(customerDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));
    }

    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto>
    fetchAccountDetails(@RequestParam("mobileNumber") String mobileNumber) {
        CustomerDto customer = accountsServiceImpl.fetchAccountDetails(mobileNumber);

        return ResponseEntity.status(HttpStatus.OK).body(customer);
    }

}
