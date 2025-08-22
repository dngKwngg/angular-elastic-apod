package com.project.demo.controller;

import com.project.demo.model.DeliveryRow;
import com.project.demo.service.data.DeliveryRowFactory;
import com.project.demo.service.export.ExcelExportService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ExportController {
    private final ExcelExportService excelExportService;

    public ExportController(ExcelExportService excelExportService) {
        this.excelExportService = excelExportService;
    }

    @GetMapping("export-delivery-rows")
    public ResponseEntity<byte[]> exportExcel() {
        List<DeliveryRow> data = DeliveryRowFactory.createDeliveryRows(5000);
        byte[] excelData = excelExportService.exportDeliveryRows(data);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=delivery_data.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(excelData);

    }
}
