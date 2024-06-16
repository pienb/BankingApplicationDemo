package com.example.demo.controller;

import com.example.demo.dto.AccountGetDto;
import com.example.demo.dto.AccountPostDto;
import com.example.demo.dto.CustomerDto;
import com.example.demo.dto.CustomerGetDto;
import com.example.demo.entity.Address;
import com.example.demo.entity.Customer;
import com.example.demo.mapper.CustomerMapper;
import com.example.demo.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class AccountControllerTest {

    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountController accountController;

    private CustomerGetDto customerGetDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        Address address = new Address(1L,"Hoofdstraat", "1100 AH", "1");
        Customer customer = new Customer(1L, "Test Naam", address , LocalDate.of(1980, 5, 15));

        customerGetDto = CustomerMapper.mapToCustomerGetDto(customer);
    }

    @Test
    public void testAddAccount() {
        // Given
        AccountPostDto accountPostDto = new AccountPostDto();
       // accountPostDto.setOwnerName("John Doe");

        AccountGetDto accountGetDto = new AccountGetDto();
        accountGetDto.setId(1L);
        accountGetDto.setCustomerGetDto(customerGetDto);

        when(accountService.createAccount(any(AccountPostDto.class))).thenReturn(accountGetDto);

        // When
        ResponseEntity<AccountGetDto> responseEntity = accountController.addAccount(accountPostDto);

        // Then
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(accountGetDto, responseEntity.getBody());
    }

    @Test
    public void testGetAccountById() {
        // Given
        Long accountId = 1L;

        AccountGetDto accountGetDto = new AccountGetDto();
        accountGetDto.setId(accountId);
        accountGetDto.setCustomerGetDto(customerGetDto);

        when(accountService.getAccountById(accountId)).thenReturn(accountGetDto);

        // When
        ResponseEntity<AccountGetDto> responseEntity = accountController.getAccountById(accountId);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(accountGetDto, responseEntity.getBody());
    }

    @Test
    public void testDeposit() {
        // Given
        Long accountId = 1L;
        Double amount = 100.0;
        Map<String, Double> request = new HashMap<>();
        request.put("amount", amount);

        AccountGetDto accountGetDto = new AccountGetDto();
        accountGetDto.setId(accountId);
        accountGetDto.setBalance(amount);

        when(accountService.deposit(accountId, amount)).thenReturn(accountGetDto);

        // When
        ResponseEntity<AccountGetDto> responseEntity = accountController.deposit(accountId, request);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(accountGetDto, responseEntity.getBody());
    }

    @Test
    public void testWithdraw() {
        // Given
        Long accountId = 1L;
        Double amount = 50.0;
        Map<String, Double> request = new HashMap<>();
        request.put("amount", amount);

        AccountGetDto accountGetDto = new AccountGetDto();
        accountGetDto.setId(accountId);
        accountGetDto.setBalance(amount);

        when(accountService.withdraw(accountId, amount)).thenReturn(accountGetDto);

        // When
        ResponseEntity<AccountGetDto> responseEntity = accountController.withdraw(accountId, request);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(accountGetDto, responseEntity.getBody());
    }
}
