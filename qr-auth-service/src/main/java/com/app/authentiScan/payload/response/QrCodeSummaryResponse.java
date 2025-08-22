package com.app.authentiScan.payload.response;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class QrCodeSummaryResponse {
    private Long totalQrCode;
    private Long todayQrCode;
    private LocalDateTime lastQrCodeCreatedDate;
    private String lastQrCodeCreatedDateFormatted;
    private Long lastQrCodeCount;

    public QrCodeSummaryResponse(Long totalQrCode, Long todayQrCode, LocalDateTime lastQrCodeCreatedDate) {
        this.totalQrCode = totalQrCode;
        this.todayQrCode = todayQrCode;
        this.lastQrCodeCreatedDate = lastQrCodeCreatedDate;
    }
}
