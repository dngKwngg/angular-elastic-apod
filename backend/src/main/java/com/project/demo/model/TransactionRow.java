package com.project.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class TransactionRow {
    private LocalDate transactionDate;
    private String customerName;
    private String productCode;
    private String productName;
    private int quantity;
    private int amount;
}
