package com.app.authentiScan.service;

import com.app.authentiScan.enums.BrandEnum;
import com.app.authentiScan.payload.dto.search.QrVerificationHistorySearchDto;
import com.app.authentiScan.payload.request.QrCodePdfRequest;
import com.app.authentiScan.payload.response.QrCodeSummaryResponse;
import com.app.authentiScan.payload.response.QrVerificationHistoryResponse;
import org.springframework.data.domain.Page;

public interface QrGenerateService {
    byte[] generateQrCodePdf(QrCodePdfRequest request);

    void generateQrCode(Integer count, String brandKey);

    QrCodeSummaryResponse getQrCodeSummary();
    Page<QrVerificationHistoryResponse> searchVerificationHistoryPage(QrVerificationHistorySearchDto searchDto);

    byte[] generateQrCodeSummaryReport(QrVerificationHistorySearchDto dto);
}
