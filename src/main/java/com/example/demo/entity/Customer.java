package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.Period;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customers")
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long customerId;

    @NonNull
    @Column(name = "name")
    String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    Address address;

    @Column(name = "date_of_birth")
    LocalDate dateOfBirth;

    public Integer getAge() {
        return Period.between(this.dateOfBirth, LocalDate.now()).getYears();
    }
}
