package com.app.authentiScan.payload.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QrCodeSummaryReportResponse {
    private Long id;
    private String uid;
    private Integer sl;
    private String verificationScanStatus;
    private Integer qrVerificationCountScan;
    private LocalDateTime lastQrVerificationScanAt;
    private String lastQrVerificationScanAtFormatted;
    private String verificationSmsStatus;
    private Integer qrVerificationCountSms;
    private LocalDateTime lastQrVerificationSmsAt;
    private String lastQrVerificationSmsAtFormatted;
    private LocalDateTime createdAt;
    private String createdAtFormatted;
    private String brandKey;
}
