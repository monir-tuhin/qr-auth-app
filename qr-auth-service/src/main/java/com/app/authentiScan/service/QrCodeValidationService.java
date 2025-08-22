package com.app.authentiScan.service;

import com.app.authentiScan.models.QrCodeValidation;
import com.app.authentiScan.payload.dto.search.QrValidationSearchDto;
import com.app.authentiScan.service.query.QrCodeValidationQueryService;
import com.app.authentiScan.utils.DateTimeFormatterUtilService;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class QrCodeValidationService {
    private final QrCodeValidationQueryService qrCodeValidationQueryService;


    public byte[] generateExcelFile(QrValidationSearchDto searchDto) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("QR Validation");

        // Create header row
        Row headerRow = sheet.createRow(0);
        String[] columns = {"SL", "QR Code", "Method", "Mobile Number", "Validation Date", "Status", "Week", "Month", "Quarter", "Year"};

        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            CellStyle style = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setBold(true);
            style.setFont(font);
            cell.setCellStyle(style);
        }

        List<QrCodeValidation> qrValidationList = qrCodeValidationQueryService.searchQrValidation(searchDto);

        // Populate data rows
        int rowNum = 1;
        for (QrCodeValidation record : qrValidationList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(rowNum-1);
            row.createCell(1).setCellValue(record.getUid());
            row.createCell(2).setCellValue(record.getValidationMethodsEnum().toString());
            row.createCell(3).setCellValue(record.getMobileNumber());
            row.createCell(4).setCellValue(DateTimeFormatterUtilService.localDateTimeToDateString(record.getValidationDate()));
            row.createCell(5).setCellValue(record.getValidationStatusEnum().toString());
            row.createCell(6).setCellValue(record.getWeekEnum().toString());
            row.createCell(7).setCellValue(record.getMonth());
            row.createCell(8).setCellValue(record.getQuarterEnum().toString());
            row.createCell(9).setCellValue(record.getYear());
        }

        // Auto-size columns
        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Write to byte array
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        return outputStream.toByteArray();
    }

}
