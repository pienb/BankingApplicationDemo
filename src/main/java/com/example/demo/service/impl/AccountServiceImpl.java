package com.example.demo.service.impl;

import com.example.demo.dto.AccountGetDto;
import com.example.demo.dto.AccountPostDto;
import com.example.demo.entity.Account;
import com.example.demo.entity.Customer;
import com.example.demo.entity.Transaction;
import com.example.demo.exception.AccountNotFoundException;
import com.example.demo.exception.BalanceLowException;
import com.example.demo.mapper.AccountMapper;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.TransactionRepository;
import com.example.demo.service.AccountService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class AccountServiceImpl implements AccountService {
    private AccountRepository accountRepository;
    private CustomerRepository customerRepository;
    private TransactionRepository transactionRepository;

    public AccountServiceImpl(AccountRepository accountRepository,
                              CustomerRepository customerRepository,
                              TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public AccountGetDto createAccount(AccountPostDto accountPost) {
        Customer customer = getCustomer(accountPost.getCustomerId());
        Account account = AccountMapper.mapToAccountFromAccountPostDto(accountPost);
        accountRepository.save(account); // Account opslaan zonder balance

        return AccountMapper.mapToAccountGetDtoFromAccount(account, customer, 0);
    }

    @Override
    public AccountGetDto getAccountById(Long id) {
        Account account = getAccount(id);
        Customer customer = getCustomer(account.getCustomerId());
        double balance = calculateBalance(account.getId());
        return AccountMapper.mapToAccountGetDtoFromAccount(account, customer, balance);
    }

  @Override
    @Transactional
    public AccountGetDto deposit(Long id, double amount) {
        Account account = getAccount(id);
        transactionRepository.save(new Transaction(
                0L, account.getId(), amount, LocalDateTime.now()));

        double balance = calculateBalance(account.getId());

        Customer customer = getCustomer(account.getCustomerId());
        return AccountMapper.mapToAccountGetDtoFromAccount(account, customer, balance);
    }

    @Override
    @Transactional
    public AccountGetDto withdraw(Long id, double amount) {
        Account account = getAccount(id);
        double currentBalance = calculateBalance(account.getId());

        if (currentBalance < amount) {
            throw new BalanceLowException("Do not have enough funds");
        }

        transactionRepository.save(new Transaction(0L, account.getId(), amount*(-1), LocalDateTime.now()));

        double balance = calculateBalance(account.getId());

        Customer customer = getCustomer(account.getCustomerId());
        return AccountMapper.mapToAccountGetDtoFromAccount(account, customer, balance);
    }

    private Account getAccount(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account does not exist"));
    }

    private Customer getCustomer(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new AccountNotFoundException("Customer does not exist"));
    }

    private double calculateBalance(Long accountId) {
        List<Transaction> transactions = transactionRepository.findByAccountId(accountId);

        return transactions.stream()
                .mapToDouble(Transaction::getAmount)
                .sum();
    }
}
