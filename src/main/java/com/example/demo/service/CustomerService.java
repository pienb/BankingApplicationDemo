package com.example.demo.service;

import com.example.demo.dto.CustomerDto;
import com.example.demo.dto.CustomerGetDto;

public interface CustomerService {

    public CustomerGetDto getCustomerById(Long id);
    public CustomerDto createCustomer(CustomerDto customerDto);
}
