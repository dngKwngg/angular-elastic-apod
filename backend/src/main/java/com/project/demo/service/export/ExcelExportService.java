package com.project.demo.service.export;

import com.project.demo.model.DeliveryRow;
import com.project.demo.model.TransactionRow;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.List;

@Service
public class ExcelExportService {

    public byte[] exportDeliveryRows(List<DeliveryRow> rows) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("PO Delivery");

            // --- Styles ---
            CellStyle centerBold = createStyle(workbook, HorizontalAlignment.CENTER, true, IndexedColors.BLACK);
            CellStyle leftBold = createStyle(workbook, HorizontalAlignment.LEFT, true, IndexedColors.BLACK);
            CellStyle rightBold = createStyle(workbook, HorizontalAlignment.RIGHT, true, IndexedColors.BLACK);
            CellStyle redLeftBold = createStyle(workbook, HorizontalAlignment.LEFT, true, IndexedColors.RED);

            // --- Headers ---
            String[] fixedHeaders = {"No", "Customer Name", "Product", "PO Date", "PO Number", "PO Quantity", "Qty Carried Forward"};
            String[] tailHeaders = {"TOTAL", "REMAINING QUANTITY", "STATUS (%)"};

            Row headerRow1 = sheet.createRow(0);
            Row headerRow2 = sheet.createRow(1);

            // Merged main headers
            createMergedCell(sheet, headerRow1, 1, 2, "INFO", centerBold);
            createMergedCell(sheet, headerRow1, 3, 5, "Month: ", centerBold);
            createMergedCell(sheet, headerRow1, 7, 37, "QUANTITY DELIVERED", centerBold);

            int col = 0;

            // Fixed headers
            for (int i = 0; i < fixedHeaders.length; i++) {
                CellStyle style = (i == fixedHeaders.length - 1) ? redLeftBold : leftBold;
                createCell(headerRow2, col++, fixedHeaders[i], style);
            }

            // Daily columns
            for (int day = 1; day <= 31; day++) {
                createCell(headerRow2, col++, String.valueOf(day), rightBold);
            }

            // Tail headers
            for (String h : tailHeaders) {
                sheet.addMergedRegion(new CellRangeAddress(0, 1, col, col));
                createCell(headerRow1, col++, h, centerBold);
            }

            // --- Data Rows ---
            int rowIndex = 2;
            for (DeliveryRow rowData : rows) {
                Row row = sheet.createRow(rowIndex++);
                fillDataRow(row, rowData, rowIndex);
            }

            // --- Column widths ---
            setDeliveryColumnWidths(sheet, fixedHeaders.length, 31, tailHeaders);

            // --- Write workbook ---
            try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
                workbook.write(output);
                return output.toByteArray();
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to export Excel", e);
        }
    }

    public byte[] exportTransactionRows(List<TransactionRow> rows, LocalDate startDate, LocalDate endDate) {
        // Placeholder for future implementation
        try (Workbook workbook = new XSSFWorkbook()){
            Sheet sheet = workbook.createSheet("Cash Sales Report");

            // Styles
            CellStyle centerBold = createStyle(workbook, HorizontalAlignment.CENTER, true, IndexedColors.BLACK);
            CellStyle leftBold = createStyle(workbook, HorizontalAlignment.LEFT, true, IndexedColors.BLACK);
            CellStyle center = createStyle(workbook, HorizontalAlignment.CENTER, false, IndexedColors.BLACK);
            CellStyle origin = createStyle(workbook, HorizontalAlignment.LEFT, false, IndexedColors.BLACK);
            String[] headers = {"Transaction Date", "Customer Name", "Product Code", "Product Name", "Quantity", "Amount"};

            Row titleRow = sheet.createRow(0);
            createCell(titleRow, 0, "Cash Sales Report", leftBold);

            Row organizationRow = sheet.createRow(1);
            createCell(organizationRow, 0, "Organization: XYZ Corp", leftBold);

            Row dateRow = sheet.createRow(2);
            createCell(dateRow, 0, "Date:", origin);
            createCell(dateRow, 1, startDate.toString() + " to " + endDate.toString(), origin);

            Row headerRow = sheet.createRow(5);
            for (int i = 0; i < headers.length; i++) {
                createCell(headerRow, i, headers[i], centerBold);
            }

            int rowIndex = 6;
            for (TransactionRow rowData : rows) {
                Row row = sheet.createRow(rowIndex++);
                int c = 0;
                createCell(row, c++, rowData.getTransactionDate().toString(), center);
                createCell(row, c++, rowData.getCustomerName(), origin);
                createCell(row, c++, rowData.getProductCode(), origin);
                createCell(row, c++, rowData.getProductName(), origin);
                createCell(row, c++, String.valueOf(rowData.getQuantity()), center);
                createCell(row, c++, String.valueOf(rowData.getAmount()), center);
            }

            // Column widths
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Write workbook
            try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
                workbook.write(output);
                return output.toByteArray();
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to export Excel", e);
        }
    }

    // --- Helper Methods ---
    private CellStyle createStyle(Workbook workbook, HorizontalAlignment alignment, boolean bold, IndexedColors color) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(bold);
        font.setColor(color.getIndex());
        style.setFont(font);
        style.setAlignment(alignment);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;
    }

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

    private void fillDataRow(Row row, DeliveryRow data, int rowIndex) {
        int c = 0;
        row.createCell(c++).setCellValue(data.getNo());
        row.createCell(c++).setCellValue(data.getCustomerName());
        row.createCell(c++).setCellValue(data.getProduct());
        row.createCell(c++).setCellValue(data.getPoDate().toString());
        row.createCell(c++).setCellValue(data.getPoNumber());
        row.createCell(c++).setCellValue(data.getPoQuantity());
        row.createCell(c++).setCellValue(data.getQtyCarriedBefore());

        for (int daily : data.getDailyDeliveries()) {
            row.createCell(c++).setCellValue(daily);
        }
        c += (31 - data.getDailyDeliveries().size());

        String rowStr = String.valueOf(rowIndex);
        row.createCell(c++).setCellFormula("SUM(H" + rowStr + ":AL" + rowStr + ")");
        row.createCell(c++).setCellFormula("F" + rowStr + "-AM" + rowStr + "+G" + rowStr);
        row.createCell(c++).setCellFormula("AL" + rowStr + "/F" + rowStr + "*100");
    }

    private void setDeliveryColumnWidths(Sheet sheet, int fixedCount, int dayCount, String[] tailHeaders) {
        for (int i = 0; i < fixedCount; i++) sheet.autoSizeColumn(i);
        for (int i = fixedCount; i < fixedCount + dayCount; i++) sheet.setColumnWidth(i, 5 * 256);
        int tailStart = fixedCount + dayCount;
        int[] tailWidths = {10, 25, 12};
        for (int i = 0; i < tailHeaders.length; i++) sheet.setColumnWidth(tailStart + i, tailWidths[i] * 256);
    }
}
