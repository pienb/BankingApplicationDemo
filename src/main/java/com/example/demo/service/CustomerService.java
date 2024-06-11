package com.example.demo.service;

import com.example.demo.dto.CustomerDto;

public interface CustomerService {

    public CustomerDto getCustomerById(Long id);
    public CustomerDto createCustomer(CustomerDto customerDto);
}
