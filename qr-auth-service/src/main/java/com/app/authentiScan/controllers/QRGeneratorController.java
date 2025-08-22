package com.app.authentiScan.controllers;

import com.app.authentiScan.enums.BrandEnum;
import com.app.authentiScan.payload.dto.search.QrVerificationHistorySearchDto;
import com.app.authentiScan.payload.request.QrCodePdfRequest;
import com.app.authentiScan.payload.response.QrCodeSummaryResponse;
import com.app.authentiScan.payload.response.QrVerificationHistoryResponse;
import com.app.authentiScan.service.QrGenerateService;
import com.app.authentiScan.utils.ApiResponseFactory;
import com.app.authentiScan.utils.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api")
@RestController
@Slf4j
@RequiredArgsConstructor
public class QRGeneratorController {

    private final QrGenerateService qrGenerateService;
    private final ApiResponseFactory apiResponseFactory;

    @PostMapping("/print-qr-code")
    public ResponseEntity<byte[]> printQrCode(@RequestBody QrCodePdfRequest request) {
        try {
            String fileName = UUID.randomUUID().toString().concat(".pdf");
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename", fileName);

            return ResponseEntity.status(HttpStatus.OK).headers(headers)
                    .body(qrGenerateService.generateQrCodePdf(request));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error in qr code report print [{}]", e.getMessage());
            return null;
        }
    }

    @PostMapping("/generate-qr-code")
    public ResponseEntity<?> save(@RequestParam Integer count, @RequestParam String brandKey) {
        qrGenerateService.generateQrCode(count, brandKey);
        return apiResponseFactory.saveResponse("QR Code Successfully Generated");
    }

    @GetMapping("/generated-qr-code-summary")
    public ResponseEntity<ApiResponse<QrCodeSummaryResponse>> getQrCodeSummary() {
        return apiResponseFactory.getResponse(qrGenerateService.getQrCodeSummary());
    }

    @PostMapping("/qr-verification-history-search-page")
    public ResponseEntity<ApiResponse<Page<QrVerificationHistoryResponse>>> qrVerificationHistory(@RequestBody QrVerificationHistorySearchDto searchDto) {
        return apiResponseFactory.getResponse(qrGenerateService.searchVerificationHistoryPage(searchDto));
    }

    @PostMapping("/print-qr-code-summary")
    public ResponseEntity<byte[]> printQrCodeSummary(@RequestBody QrVerificationHistorySearchDto dto) {
        try {
            String fileName = UUID.randomUUID().toString().concat(".pdf");
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename", fileName);

            return ResponseEntity.status(HttpStatus.OK).headers(headers)
                    .body(qrGenerateService.generateQrCodeSummaryReport(dto));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error in qr code summary report print [{}]", e.getMessage());
            return null;
        }
    }

}
