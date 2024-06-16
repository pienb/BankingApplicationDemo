package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import com.example.demo.dto.AccountGetDto;
import com.example.demo.dto.AccountPostDto;
import com.example.demo.dto.CustomerGetDto;
import com.example.demo.entity.Account;
import com.example.demo.entity.Customer;
import com.example.demo.entity.Transaction;
import com.example.demo.exception.BalanceLowException;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.TransactionRepository;
import com.example.demo.service.impl.AccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private AccountServiceImpl accountService;

    private Customer customer;
    private Account account;

    @BeforeEach
    public void setUp() {
        customer = new Customer();
        customer.setCustomerId(1L);
        customer.setName("Test Customer");
        customer.setDateOfBirth(LocalDate.now());

        account = new Account();
        account.setId(1L);
        account.setCustomerId(1L);
        account.setIBan("NL91ABNA0417164300");

    }

    @Test
    public void testCreateAccount() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        AccountPostDto accountPostDto = new AccountPostDto();
        accountPostDto.setCustomerId(1L);

        AccountGetDto accountGetDto = accountService.createAccount(accountPostDto);

        assertNotNull(accountGetDto);
        verify(accountRepository, times(1)).save(any(Account.class));
    }

    @Test
    public void testGetAccountById() {
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(transactionRepository.findByAccountId(1L)).thenReturn(Arrays.asList(
                new Transaction(1L, 1L, 100.0, LocalDateTime.now())
        ));

        AccountGetDto accountGetDto = accountService.getAccountById(1L);

        assertNotNull(accountGetDto);
        assertEquals(1L, accountGetDto.getId());
        assertEquals(100.0, accountGetDto.getBalance());
    }

    @Test
    public void testDeposit() {
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(transactionRepository.findByAccountId(1L)).thenReturn(Arrays.asList(
                new Transaction(1L, 1L, 100.0, LocalDateTime.now())
        ));

        AccountGetDto accountGetDto = accountService.deposit(1L, 50.0);

        assertNotNull(accountGetDto);
        assertEquals(1L, accountGetDto.getId());
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    public void testWithdraw() {
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(transactionRepository.findByAccountId(1L)).thenReturn(Arrays.asList(
                new Transaction(1L, 1L, 200.0, LocalDateTime.now())
        ));

        AccountGetDto accountGetDto = accountService.withdraw(1L, 100.0);

        assertNotNull(accountGetDto);
        assertEquals(1L, accountGetDto.getId());
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    public void testWithdrawWithLowBalance() {
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(transactionRepository.findByAccountId(1L)).thenReturn(Arrays.asList(
                new Transaction(1L, 1L, 50.0, LocalDateTime.now())
        ));

        assertThrows(BalanceLowException.class, () -> accountService.withdraw(1L, 100.0));
    }
}

