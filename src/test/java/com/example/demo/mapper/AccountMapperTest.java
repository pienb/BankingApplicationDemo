package com.example.demo.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.demo.dto.AccountGetDto;
import com.example.demo.dto.AccountPostDto;
import com.example.demo.dto.CustomerDto;
import com.example.demo.entity.Account;
import com.example.demo.entity.Address;
import com.example.demo.entity.Customer;
import org.junit.jupiter.api.Test;

public class AccountMapperTest {

    @Test
    public void testMapToAccountFromAccountPostDto() {
        AccountPostDto accountPostDto = new AccountPostDto();
        accountPostDto.setId(1L);
        accountPostDto.setCustomerId(100L);
        accountPostDto.setIBan("NL91ABNA0417164300");

        Account account = AccountMapper.mapToAccountFromAccountPostDto(accountPostDto);

        assertThat(account).isNotNull();
        assertThat(account.getId()).isEqualTo(1L);
        assertThat(account.getCustomerId()).isEqualTo(100L);
        assertThat(account.getIBan()).isEqualTo("NL91ABNA0417164300");
    }

    @Test
    public void testMapToAccountGetDtoFromAccount() {
        Account account = new Account(1L, 100L, "NL91ABNA0417164300");
        Address address = new Address(1L,"Kerkstraat", "1122 BH", "2");

        CustomerDto customerDto = new CustomerDto();
        customerDto.setCustomerId(100L);
        customerDto.setName("John Doe");
        customerDto.setAddress(address);
        customerDto.setDateOfBirth("18-09-1995");

        Customer customer = CustomerMapper.mapToCustomer(customerDto);

        double balance = 1500.0;

        AccountGetDto accountGetDto = AccountMapper.mapToAccountGetDtoFromAccount(account, customer, balance);

        assertThat(accountGetDto).isNotNull();
        assertThat(accountGetDto.getId()).isEqualTo(1L);
        assertThat(accountGetDto.getCustomerGetDto().getCustomerId()).isEqualTo(customerDto.getCustomerId());
        assertThat(accountGetDto.getCustomerGetDto().getAddress()).isEqualTo(customerDto.getAddress());
        assertThat(accountGetDto.getCustomerGetDto().getAge()).isEqualTo(28);

        assertThat(accountGetDto.getIBan()).isEqualTo("NL91ABNA0417164300");
        assertThat(accountGetDto.getBalance()).isEqualTo(1500.0);
        assertThat(accountGetDto.getBalance()).isEqualTo(1500.0);

    }
}
