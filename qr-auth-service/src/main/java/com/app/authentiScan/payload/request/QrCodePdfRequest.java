package com.app.authentiScan.payload.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QrCodePdfRequest {
    private Integer countFrom;
    private Integer countTo;
}
