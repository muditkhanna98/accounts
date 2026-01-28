package com.mudit.accounts.mappers;

import com.mudit.accounts.dtos.AccountsDto;
import com.mudit.accounts.entities.Accounts;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AccountsStructMapper {
    Accounts toAccountsEntity(AccountsDto accountsDto);

    AccountsDto toAccountsDto(Accounts accounts);

    void update(AccountsDto accountsDto, @MappingTarget Accounts accounts);
}
