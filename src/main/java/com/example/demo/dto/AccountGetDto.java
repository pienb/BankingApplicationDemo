package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccountGetDto {

    Long id;

    @JsonProperty("customer")
    CustomerGetDto customerGetDto;

    @JsonProperty("iban")
    String iBan;

    @JsonProperty("balance")
    Double balance;
}

