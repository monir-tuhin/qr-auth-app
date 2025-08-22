package com.app.authentiScan.payload.response;

import com.app.authentiScan.enums.ValidationStatusEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class QrStatusSummaryResponse {
    private String name;
    private Long value;

    public QrStatusSummaryResponse(String name, Long value) {
        this.name = name;
        this.value = value;
    }
}
