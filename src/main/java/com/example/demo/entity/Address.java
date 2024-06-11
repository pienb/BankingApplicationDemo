package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "address")
@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long addressId;

    @JsonProperty("street")
    @Column(name = "street")
    String street;

    @JsonProperty("zip_code")
    @Column(name = "zip_code")
    String zipCode;

    @JsonProperty("house_number")
    @Column(name = "house_number")
    String houseNumber;

}
