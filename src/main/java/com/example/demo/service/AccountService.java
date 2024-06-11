package com.example.demo.service;

import com.example.demo.dto.AccountGetDto;
import com.example.demo.dto.AccountPostDto;

public interface AccountService {

    AccountGetDto createAccount(AccountPostDto accountPostDtoGetDto);

    AccountGetDto getAccountById(Long id);

    AccountGetDto deposit(Long id, double amount);

    AccountGetDto withdraw(Long id, double amount);

}
