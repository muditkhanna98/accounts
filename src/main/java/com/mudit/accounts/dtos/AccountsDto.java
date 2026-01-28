package com.mudit.accounts.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AccountsDto {
    private Long accountNumber;

    private String accountType;

    private String branchAddress;
}
