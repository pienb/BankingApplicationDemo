package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccountPostDto {

    Long id;

    @JsonProperty("customer_id")
    Long customerId;

    @JsonProperty("iban")
    String iBan;
}
