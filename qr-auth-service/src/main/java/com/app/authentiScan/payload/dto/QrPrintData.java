package com.app.authentiScan.payload.dto;

import com.app.authentiScan.models.QrData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QrPrintData {
    private List<QrData> qrDataList;
}
