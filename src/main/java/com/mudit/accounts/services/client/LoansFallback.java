package com.mudit.accounts.services.client;

import com.mudit.accounts.dtos.LoansDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
class LoansFallback implements LoansFeignClient {

    @Override
    public ResponseEntity<LoansDto> fetchLoanDetails(String mobileNumber) {
        return null;
    }
}
