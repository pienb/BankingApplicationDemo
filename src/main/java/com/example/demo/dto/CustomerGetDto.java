package com.example.demo.dto;

import com.example.demo.entity.Address;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Period;

@AllArgsConstructor
@NoArgsConstructor
@Data
// post
public class CustomerGetDto {
    @JsonProperty("customer_id")
    Long customerId;
    @JsonProperty("name")
    String name;
    @JsonProperty("address")
    Address address;
    @JsonProperty("age")
    Integer age;

    public int getAge(LocalDate dateOfBirth) {
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }



}
