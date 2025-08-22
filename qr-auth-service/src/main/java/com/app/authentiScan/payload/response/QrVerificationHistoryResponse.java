package com.app.authentiScan.payload.response;

import com.app.authentiScan.enums.VerificationStatusEnum;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QrVerificationHistoryResponse {
    private Long id;
    private String uid;
    private Integer sl;
    private VerificationStatusEnum verificationScanStatusEnum;
    private Integer qrVerificationCountScan;
    private LocalDateTime lastQrVerificationScanAt;
    private String lastQrVerificationScanAtFormatted;
    private VerificationStatusEnum verificationSmsStatusEnum;
    private Integer qrVerificationCountSms;
    private LocalDateTime lastQrVerificationSmsAt;
    private String lastQrVerificationSmsAtFormatted;
    private LocalDateTime updatedAt;
    private String brandKey;
}
