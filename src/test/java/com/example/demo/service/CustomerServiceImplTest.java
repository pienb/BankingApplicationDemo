package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

import com.example.demo.dto.CustomerGetDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.dto.CustomerDto;
import com.example.demo.entity.Customer;
import com.example.demo.exception.AccountNotFoundException;
import com.example.demo.mapper.CustomerMapper;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.service.impl.CustomerServiceImpl;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    private Customer customer;
    private CustomerDto customerDto;

    @BeforeEach
    public void setUp() {
        customer = new Customer();
        customer.setCustomerId(1L);
        customer.setName("Test Customer");
//        customer.setAddress();

        customerDto = new CustomerDto();
        customerDto.setCustomerId(1L);
        customerDto.setName("Test Customer");
    }

    @Test
    public void testCreateCustomer() {
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        CustomerDto result = customerService.createCustomer(customerDto);

        assertNotNull(result);
        assertEquals(1L, result.getCustomerId());
        assertEquals("Test Customer", result.getName());
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    public void testGetCustomerById() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        CustomerGetDto result = customerService.getCustomerById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getCustomerId());
        assertEquals("Test Customer", result.getName());
        verify(customerRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetCustomerByIdNotFound() {
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> customerService.getCustomerById(1L));
        verify(customerRepository, times(1)).findById(1L);
    }
}

