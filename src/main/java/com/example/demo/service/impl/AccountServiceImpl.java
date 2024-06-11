package com.example.demo.service.impl;

import com.example.demo.dto.AccountGetDto;
import com.example.demo.dto.AccountPostDto;
import com.example.demo.entity.Account;
import com.example.demo.entity.Customer;
import com.example.demo.entity.Transaction;
import com.example.demo.exception.AccountNotFoundException;
import com.example.demo.exception.BalanceLowException;
import com.example.demo.mapper.AccountMapper;
import com.example.demo.mapper.CustomerMapper;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.TransactionRepository;
import com.example.demo.service.AccountService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/*@Service
public class AccountServiceImpl implements AccountService {
    private AccountRepository accountRepository;
    private CustomerRepository customerRepository;

    public AccountServiceImpl(AccountRepository accountRepository, CustomerRepository customerRepository) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public AccountGetDto createAccount(AccountPostDto accountPost) {
        Customer customer = getCustomer(accountPost.getCustomerId());
        Account account = AccountMapper.mapToAccountFromAccountPostDto(accountPost);
        return AccountMapper.mapToAccountGetDtoFromAccount(accountRepository.save(account), CustomerMapper.mapToCustomerDto(customer));
    }

    @Override
    public AccountGetDto getAccountById(Long id) {
        Account account = getAccount(id);
        Customer customer = getCustomer(account.getCustomerId());
        return AccountMapper.mapToAccountGetDtoFromAccount(account, CustomerMapper.mapToCustomerDto(customer));
    }

    @Override
    @Transactional
    public AccountGetDto deposit(Long id, double amount) {
        Account account = getAccount(id);
        double total = account.getBalance() + amount;
        account.setBalance(total);
        accountRepository.save(account);
        Customer customer = getCustomer(account.getCustomerId());
        return AccountMapper.mapToAccountGetDtoFromAccount(account, CustomerMapper.mapToCustomerDto(customer));
    }

    @Override
    @Transactional
    public AccountGetDto withdraw(Long id, double amount) {
        Account account = getAccount(id);
        double total = account.getBalance() - amount;
        if (total < 0) {
            throw new BalanceLowException("Do not have enough funds");
        }
        account.setBalance(total);
        accountRepository.save(account);
        Customer customer = getCustomer(account.getCustomerId());
        return AccountMapper.mapToAccountGetDtoFromAccount(account, CustomerMapper.mapToCustomerDto(customer));
    }

    private Account getAccount(Long id) {
        Account account = accountRepository
                .findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account does not exist"));
        return account;
    }

    private Customer getCustomer(Long customerId) {
        Customer customer = customerRepository
                .findById(customerId)
                .orElseThrow(() -> new AccountNotFoundException("Account does not exist"));
        return customer;
    }
}*/
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

        // Je kunt hier een initialisatie transactie toevoegen om het saldo te starten
        // Voorbeeld: transactionRepository.save(new Transaction(account.getId(), initialBalance, LocalDateTime.now()));

        return AccountMapper.mapToAccountGetDtoFromAccount(account, CustomerMapper.mapToCustomerDto(customer), 0);
    }

    @Override
    public AccountGetDto getAccountById(Long id) {
        Account account = getAccount(id);
        Customer customer = getCustomer(account.getCustomerId());
        double balance = calculateBalance(account.getId());
        return AccountMapper.mapToAccountGetDtoFromAccount(account, CustomerMapper.mapToCustomerDto(customer), balance);
    }

  // Deze hoeft ook eigenlijk niet dus als ik foutmeldingen krijg die ik niet kan oplossen dan weglaten.
  // Mocht ik tijd over hebben dan kan ik dit proberen te fixen als extra.
  @Override
    @Transactional
    public AccountGetDto deposit(Long id, double amount) {
        Account account = getAccount(id);
        // Opslaan van transactie
        transactionRepository.save(new Transaction(
                // Dit is om de constructor te bepalen maar het moet eigenlijk niet null zijn maar weet niet wat anders
                0L, account.getId(), amount, LocalDateTime.now()));

        // Herbereken balans
        double balance = calculateBalance(account.getId());

        Customer customer = getCustomer(account.getCustomerId());
        return AccountMapper.mapToAccountGetDtoFromAccount(account, CustomerMapper.mapToCustomerDto(customer), balance);
    }

    @Override
    @Transactional
    public AccountGetDto withdraw(Long id, double amount) {
        Account account = getAccount(id);
        double currentBalance = calculateBalance(account.getId());

        if (currentBalance < amount) {
            throw new BalanceLowException("Do not have enough funds");
        }

        // Opslaan van transactie (negatief bedrag voor opname)
        // het transactienummer meegeven. Maar die wordt automatisch berekend dus hoe moet dat? uitzoeken!!
        transactionRepository.save(new Transaction(0L, account.getId(), amount*(-1), LocalDateTime.now()));

        // Herbereken balans
        double balance = calculateBalance(account.getId());

        Customer customer = getCustomer(account.getCustomerId());
        return AccountMapper.mapToAccountGetDtoFromAccount(account, CustomerMapper.mapToCustomerDto(customer), balance);
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

        // Bereken totale balans op basis van transacties
        return transactions.stream()
                .mapToDouble(Transaction::getAmount)
                .sum();
    }
}
