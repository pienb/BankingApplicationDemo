package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transactions")
@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    // Dit is de PK naar account tabel.
    @Column(name = "account_id")
    Long accountId;

    @Column(name = "amount")
    double amount;

    @Column(name = "timestamp")
    LocalDateTime timestamp;


}
