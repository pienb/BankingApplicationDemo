package com.example.demo.mapper;

import com.example.demo.dto.AccountGetDto;
import com.example.demo.dto.AccountPostDto;
import com.example.demo.dto.CustomerDto;
import com.example.demo.entity.Account;

public class AccountMapper {

    public static Account mapToAccountFromAccountPostDto(AccountPostDto accountPost){
        Account account = new Account(
                accountPost.getId(),
                accountPost.getCustomerId(),
                accountPost.getIBan()
        );
        return account;
    }

    public static AccountGetDto mapToAccountGetDtoFromAccount(Account account, CustomerDto customerDto, double balance){
        AccountGetDto accountGetDto = new AccountGetDto(
                account.getId(),
                customerDto,
                account.getIBan(),
                balance
        );
        return accountGetDto;
    }
}
