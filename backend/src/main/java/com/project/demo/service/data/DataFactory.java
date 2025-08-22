package com.project.demo.service.data;

import com.project.demo.model.DeliveryRow;
import com.project.demo.model.TransactionRow;

import java.time.LocalDate;
import java.util.List;

public class DataFactory {
    public static List<DeliveryRow> createDeliveryRows(int quantity) {
        return List.of(
                new DeliveryRow(1, "Customer A", "Product X", LocalDate.now(), "PO12345", quantity,
                        List.of(10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 110, 120,
                                130, 140, 150, 170, 180, 190, 200,
                                220, 230, 240, 250, 260, 270, 280, 290, 300
                                ), 200),
                new DeliveryRow(2, "Customer B", "Product Y", LocalDate.now(), "PO67890", quantity,
                        List.of(5, 15, 25, 35, 45, 55, 65, 75, 85, 95,
                                105, 115, 125, 135, 145,
                                155, 165, 175, 185,
                                195), 500)
        );
    }

    public static List<TransactionRow> createTransactionRows(){
        return List.of(
                new TransactionRow(LocalDate.now(), "Customer A", "P001", "Product X", 10, 100),
                new TransactionRow(LocalDate.now(), "Customer B", "P002", "Product Y", 5, 50),
                new TransactionRow(LocalDate.now(), "Customer C", "P003", "Product Z", 20, 200)
        );
    }
}
