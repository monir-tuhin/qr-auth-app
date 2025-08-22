package com.app.authentiScan.service.mapper;

import com.app.authentiScan.models.QrData;
import com.app.authentiScan.payload.response.QrCodeVerificationResponse;

public class CustomerFeedbackMapper {

    public static QrCodeVerificationResponse mapToQrCodeVerificationResponseFromQrData(QrData qrData, boolean customerFeedbackSubmitted) {
        return QrCodeVerificationResponse.builder()
                .id(qrData.getId())
                .uid(qrData.getUid())
                .sl(qrData.getSl())
                .verificationScanStatusEnum(qrData.getVerificationScanStatusEnum())
                .qrVerificationCountScan(qrData.getQrVerificationCountScan())
                .verificationSmsStatusEnum(qrData.getVerificationSmsStatusEnum())
                .qrVerificationCountSms(qrData.getQrVerificationCountSms())
                .lastQrVerificationScanAt(qrData.getLastQrVerificationScanAt())
                .lastQrVerificationSmsAt(qrData.getLastQrVerificationSmsAt())
                .customerFeedbackSubmitted(customerFeedbackSubmitted)
                .build();
    }

}
