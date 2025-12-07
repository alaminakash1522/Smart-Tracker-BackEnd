package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String source;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    @Column(length = 255)
    private String description;

    @Column(nullable = false)
    private boolean transactionType;

    @Column(nullable = true)
    private LocalDate transactionAt;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // Automatically set timestamp before saving
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public boolean gettransactionType(){
        return this.transactionType;
    }
}
