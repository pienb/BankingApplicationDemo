package com.example.demo.service.impl;

import com.example.demo.dto.CustomerDto;
import com.example.demo.dto.CustomerGetDto;
import com.example.demo.entity.Customer;
import com.example.demo.exception.AccountNotFoundException;
import com.example.demo.mapper.CustomerMapper;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.service.CustomerService;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    @Override
    public CustomerDto createCustomer(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapToCustomer(customerDto);
        Customer savedCustomerDto = customerRepository.save(customer);
        return CustomerMapper.mapToCustomerDto(savedCustomerDto);
    }

    @Override
    public CustomerGetDto getCustomerById(Long id) {
        Customer customer = customerRepository
                .findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account does not exist"));
        return CustomerMapper.mapToCustomerGetDto(customer);
    }

}
