package com.example.demo.controller;

import com.example.demo.dto.CustomerDto;
import com.example.demo.dto.CustomerGetDto;
import com.example.demo.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateCustomer() {
        // Given
        CustomerDto customerDto = new CustomerDto();
        customerDto.setName("John Doe");

        CustomerDto createdCustomerDto = new CustomerDto();
        createdCustomerDto.setCustomerId(1L);
        createdCustomerDto.setName("John Doe");

        when(customerService.createCustomer(any(CustomerDto.class))).thenReturn(createdCustomerDto);

        // When
        CustomerDto response = customerController.createCustomer(customerDto);

        // Then
        assertEquals(createdCustomerDto, response);
    }

    @Test
    public void testGetCustomer() {
        // Given
        Long customerId = 1L;

        CustomerGetDto customerGetDto = new CustomerGetDto();
        customerGetDto.setCustomerId(customerId);
        customerGetDto.setName("Jane Smith");

        when(customerService.getCustomerById(customerId)).thenReturn(customerGetDto);

        // When
        CustomerGetDto response = customerController.getCustomer(customerId);

        // Then
        assertEquals(customerGetDto, response);
    }
}
