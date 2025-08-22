package com.app.authentiScan.controllers;

import com.app.authentiScan.payload.dto.search.QrValidationSearchDto;
import com.app.authentiScan.service.QrCodeValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/qr-validation")
@RequiredArgsConstructor
public class QrCodeValidationController {
    private final QrCodeValidationService qrCodeValidationService;

    @PostMapping("/download-excel")
    public ResponseEntity<byte[]> downloadExcel(@RequestBody QrValidationSearchDto searchDto) throws IOException {
        byte[] excelData = qrCodeValidationService.generateExcelFile(searchDto);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=validation_records.xlsx");
        headers.add("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

        return new ResponseEntity<>(excelData, headers, HttpStatus.OK);
    }


}
