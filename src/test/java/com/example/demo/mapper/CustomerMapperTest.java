package com.example.demo.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.demo.dto.CustomerDto;
import com.example.demo.entity.Address;
import com.example.demo.entity.Customer;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CustomerMapperTest {

    @Test
    public void testMapToCustomerDto() {
        Address address = new Address(1L,"Hoofdstraat", "1100 AH", "1");
        Customer customer = new Customer(1L, "Test Naam", address , LocalDate.of(1980, 5, 15));

        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer);

        assertThat(customerDto).isNotNull();
        assertThat(customerDto.getCustomerId()).isEqualTo(1L);
        assertThat(customerDto.getName()).isEqualTo("Test Naam");
        assertThat(customerDto.getAddress()).isEqualTo(address);
        assertThat(customerDto.getDateOfBirth()).isEqualTo("1980-05-15");
    }

    @Test
    public void testMapToCustomer() {
        Address address = new Address(1L,"Kerkstraat", "1122 BH", "2");
        CustomerDto customerDto = new CustomerDto(1L, "Test Naam", address, "20-10-1990");

        Customer customer = CustomerMapper.mapToCustomer(customerDto);

        assertThat(customer).isNotNull();
        assertThat(customer.getCustomerId()).isEqualTo(1L);
        assertThat(customer.getName()).isEqualTo("Test Naam");
        assertThat(customer.getAddress()).isEqualTo(address);
        assertThat(customer.getDateOfBirth()).isEqualTo(LocalDate.of(1990, 10, 20));
    }
}
