package com.project.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor

public class DeliveryRow {
    private int no;
    private String customerName;
    private String product;
    private LocalDate poDate;
    private String poNumber;
    private int poQuantity;
    private int qtyCarriedBefore;

    // deliveries for 31 days (index 0 = day1, ..., index 30 = day31)
    private List<Integer> dailyDeliveries;

    // calculated
    private int totalDelivered;
    private int remainingQuantity;
    private double statusPercent;

    private void calculateStats(){
        this.totalDelivered = dailyDeliveries.stream().mapToInt(Integer::intValue).sum();
        this.remainingQuantity = poQuantity - totalDelivered + qtyCarriedBefore;
        this.statusPercent = (double) totalDelivered / poQuantity * 100;
    }

    public DeliveryRow(int no, String customerName, String product, LocalDate poDate, String poNumber, int poQuantity, List<Integer> dailyDeliveries, int qtyCarriedBefore) {
        this.no = no;
        this.customerName = customerName;
        this.product = product;
        this.poDate = poDate;
        this.poNumber = poNumber;
        this.poQuantity = poQuantity;
        this.dailyDeliveries = dailyDeliveries;
        this.qtyCarriedBefore = qtyCarriedBefore;
        calculateStats();
    }
}
