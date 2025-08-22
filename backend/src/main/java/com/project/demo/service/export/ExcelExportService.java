package com.project.demo.service.export;

import com.project.demo.model.DeliveryRow;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class ExcelExportService {

    public byte[] exportDeliveryRows(List<DeliveryRow> rows) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("PO Delivery");

        // --- Styles ---
        Font boldFont = workbook.createFont();
        boldFont.setBold(true);

        Font redBoldFont = workbook.createFont();
        redBoldFont.setBold(true);
        redBoldFont.setColor(IndexedColors.RED.getIndex());

        CellStyle centerStyle = workbook.createCellStyle();
        centerStyle.setAlignment(HorizontalAlignment.CENTER);
        centerStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        CellStyle leftBoldStyle = workbook.createCellStyle();
        leftBoldStyle.setAlignment(HorizontalAlignment.LEFT);
        leftBoldStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        leftBoldStyle.setFont(boldFont);

        CellStyle rightBoldStyle = workbook.createCellStyle();
        rightBoldStyle.setAlignment(HorizontalAlignment.RIGHT);
        rightBoldStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        rightBoldStyle.setFont(boldFont);

        CellStyle qtyCarriedStyle = workbook.createCellStyle();
        qtyCarriedStyle.setAlignment(HorizontalAlignment.LEFT);
        qtyCarriedStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        qtyCarriedStyle.setFont(redBoldFont);

        // --- Headers ---
        String[] fixedHeaders = {"No", "Customer Name", "Product", "PO Date", "PO Number", "PO Quantity", "Qty Carried Forward"};
        String[] tailHeaders = {"TOTAL", "REMAINING QUANTITY", "STATUS (%)"};

        Row headerRow1 = sheet.createRow(0);
        Row headerRow2 = sheet.createRow(1);

        // Merged main headers
        createMergedCell(sheet, headerRow1, 1, 2, "INFO", centerStyle);
        createMergedCell(sheet, headerRow1, 3, 5, "Month: ", centerStyle);
        createMergedCell(sheet, headerRow1, 7, 37, "QUANTITY DELIVERED", centerStyle);

        int col = 0;

        // Fixed headers
        for (int i = 0; i < fixedHeaders.length; i++) {
            if (i != fixedHeaders.length - 1) {
                createCell(headerRow2, col++, fixedHeaders[i], leftBoldStyle);
            } else {
                // Qty Carried Forward with special style
                createCell(headerRow2, col++, fixedHeaders[i], qtyCarriedStyle);
            }
        }

        // Daily columns
        for (int day = 1; day <= 31; day++) {
            createCell(headerRow2, col++, String.valueOf(day), rightBoldStyle);
        }

        // Tail headers
        for (String h : tailHeaders) {
            sheet.addMergedRegion(new CellRangeAddress(0, 1, col, col));
            createCell(headerRow1, col++, h, centerStyle);
        }

        // --- Data Rows ---
        int rowIndex = 2;
        for (DeliveryRow rowData : rows) {
            Row row = sheet.createRow(rowIndex++);
            int c = 0;

            row.createCell(c++).setCellValue(rowData.getNo());
            row.createCell(c++).setCellValue(rowData.getCustomerName());
            row.createCell(c++).setCellValue(rowData.getProduct());
            row.createCell(c++).setCellValue(rowData.getPoDate().toString());
            row.createCell(c++).setCellValue(rowData.getPoNumber());
            row.createCell(c++).setCellValue(rowData.getPoQuantity());
            row.createCell(c++).setCellValue(rowData.getQtyCarriedBefore());

            for (int daily : rowData.getDailyDeliveries()) {
                row.createCell(c++).setCellValue(daily);
            }
            c += (31 - rowData.getDailyDeliveries().size());

            // Formulas
            String rowStr = String.valueOf(rowIndex);
            row.createCell(c++).setCellFormula("SUM(H" + rowStr + ":AL" + rowStr + ")");
            row.createCell(c++).setCellFormula("F" + rowStr + "-AM" + rowStr + "+G" + rowStr);
            row.createCell(c++).setCellFormula("AL" + rowStr + "/F" + rowStr + "*100");
        }

        // --- Column widths ---
        // Fixed headers
        for (int i = 0; i < fixedHeaders.length; i++) sheet.autoSizeColumn(i);
        // Daily columns
        for (int i = fixedHeaders.length; i < fixedHeaders.length + 31; i++) sheet.setColumnWidth(i, 5 * 256);
        // Tail headers
        int tailStart = fixedHeaders.length + 31;
        int[] tailWidths = {10, 25, 12};
        for (int i = 0; i < tailHeaders.length; i++) sheet.setColumnWidth(tailStart + i, tailWidths[i] * 256);

        // --- Write workbook ---
        try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            workbook.write(output);
            workbook.close();
            return output.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Failed to export Excel", e);
        }
    }

    // --- Helper Methods ---
    private void createCell(Row row, int col, String value, CellStyle style) {
        Cell cell = row.createCell(col);
        cell.setCellValue(value);
        cell.setCellStyle(style);
    }

    private void createMergedCell(Sheet sheet, Row row, int colStart, int colEnd, String value, CellStyle style) {
        sheet.addMergedRegion(new CellRangeAddress(row.getRowNum(), row.getRowNum(), colStart, colEnd));
        Cell cell = row.createCell(colStart);
        cell.setCellValue(value);
        cell.setCellStyle(style);
    }
}
