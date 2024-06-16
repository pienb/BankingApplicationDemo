package com.example.demo.mapper;

import com.example.demo.dto.AccountGetDto;
import com.example.demo.dto.AccountPostDto;
import com.example.demo.entity.Account;
import com.example.demo.entity.Customer;

public class AccountMapper {

    public static Account mapToAccountFromAccountPostDto(AccountPostDto accountPost){
        Account account = new Account(
                accountPost.getId(),
                accountPost.getCustomerId(),
                accountPost.getIBan()
        );
        return account;
    }

    public static AccountGetDto mapToAccountGetDtoFromAccount(Account account, Customer customer, double balance){
        AccountGetDto accountGetDto = new AccountGetDto(
                account.getId(),
                CustomerMapper.mapToCustomerGetDto(customer),
                account.getIBan(),
                balance
        );
        return accountGetDto;
    }
}
