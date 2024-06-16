package com.example.demo.mapper;

import com.example.demo.dto.CustomerDto;
import com.example.demo.dto.CustomerGetDto;
import com.example.demo.entity.Customer;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class CustomerMapper {
    public static CustomerDto mapToCustomerDto(Customer customer){
        CustomerDto customerDto = new CustomerDto(
                customer.getCustomerId(),
                customer.getName(),
                customer.getAddress(),
                customer.getDateOfBirth().toString()
                      );
        return customerDto;
    }

    public static Customer mapToCustomer(CustomerDto customerDto){
        Customer customer = new Customer(
                customerDto.getCustomerId(),
                customerDto.getName(),
                customerDto.getAddress(),
                LocalDate.parse(customerDto.getDateOfBirth(), DateTimeFormatter.ofPattern("dd-MM-yyyy"))
        );
        return customer;
    }


    public static CustomerGetDto mapToCustomerGetDto(Customer customer){
        CustomerGetDto customerGetDto = new CustomerGetDto(
                customer.getCustomerId(),
                customer.getName(),
                customer.getAddress(),
                customer.getAge()

        );
        return customerGetDto;
    }
    public int getAge(LocalDate dateOfBirth) {
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }

}
