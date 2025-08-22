package com.app.authentiScan.payload.dto.search;

import com.app.authentiScan.enums.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QrValidationSearchDto{
    private String uid;
    private ValidationMethodsEnum validationMethodsEnum;
    private ValidationStatusEnum validationStatusEnum;
    private String mobileNumber;
    private LocalDateTime validationDate;
    private WeekEnum weekEnum;
    private String month;
    private QuarterEnum quarterEnum;
    private Integer year;
    private String searchTerm;
    private ChartTypeEnum chartTypeEnum;
    private DashboardTypeEnum dashboardTypeEnum;
}
