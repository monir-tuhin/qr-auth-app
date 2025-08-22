package com.app.authentiScan.service.mapper;

import com.app.authentiScan.models.QrData;
import com.app.authentiScan.payload.response.QrCodeSummaryReportResponse;
import com.app.authentiScan.payload.response.QrVerificationHistoryResponse;
import com.app.authentiScan.utils.DateTimeFormatterUtilService;

public class QrGeneratorMapper {
    public static QrVerificationHistoryResponse mapToQrVerificationHistoryResponseFromQrData(QrData qrData) {
        return QrVerificationHistoryResponse.builder()
                .id(qrData.getId())
                .uid(qrData.getUid())
                .sl(qrData.getSl())
                .verificationScanStatusEnum(qrData.getVerificationScanStatusEnum())
                .qrVerificationCountScan(qrData.getQrVerificationCountScan())
                .lastQrVerificationScanAt(qrData.getLastQrVerificationScanAt())
                .lastQrVerificationScanAtFormatted(DateTimeFormatterUtilService.localDateTimeToDateTimeString(qrData.getLastQrVerificationScanAt()))
                .verificationSmsStatusEnum(qrData.getVerificationSmsStatusEnum())
                .qrVerificationCountSms(qrData.getQrVerificationCountSms())
                .lastQrVerificationSmsAt(qrData.getLastQrVerificationSmsAt())
                .lastQrVerificationSmsAtFormatted(DateTimeFormatterUtilService.localDateTimeToDateTimeString(qrData.getLastQrVerificationSmsAt()))
                .updatedAt(qrData.getUpdatedAt())
                .brandKey(qrData.getBrandKey())
                .build();
    }

    public static QrCodeSummaryReportResponse mapToQrSummaryReportResponseFromQrData(QrData qrData) {
        return QrCodeSummaryReportResponse.builder()
                .id(qrData.getId())
                .uid(qrData.getUid())
                .sl(qrData.getSl())
                .verificationScanStatus(qrData.getVerificationScanStatusEnum().toString())
                .qrVerificationCountScan(qrData.getQrVerificationCountScan())
                .lastQrVerificationScanAt(qrData.getLastQrVerificationScanAt())
                .lastQrVerificationScanAtFormatted(DateTimeFormatterUtilService.localDateTimeToDateTimeString(qrData.getLastQrVerificationScanAt()))
                .verificationSmsStatus(qrData.getVerificationSmsStatusEnum().toString())
                .qrVerificationCountSms(qrData.getQrVerificationCountSms())
                .lastQrVerificationSmsAt(qrData.getLastQrVerificationSmsAt())
                .lastQrVerificationSmsAtFormatted(DateTimeFormatterUtilService.localDateTimeToDateTimeString(qrData.getLastQrVerificationSmsAt()))
                .createdAt(qrData.getCreatedAt())
                .createdAtFormatted(DateTimeFormatterUtilService.localDateTimeToDateTimeString(qrData.getCreatedAt()))
                .brandKey(qrData.getBrandKey())
                .build();
    }
}
