package com.app.authentiScan.service;

import com.app.authentiScan.enums.ChartTypeEnum;
import com.app.authentiScan.enums.DashboardTypeEnum;
import com.app.authentiScan.payload.dto.search.QrValidationSearchDto;
import com.app.authentiScan.payload.response.QrStatusSummaryResponse;
import com.app.authentiScan.repository.CustomerFeedbackRepository;
import com.app.authentiScan.repository.QrCodeValidationRepository;
import com.app.authentiScan.repository.QrDataRepository;
import com.app.authentiScan.service.query.QrCodeValidationQueryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class DashboardService {

    private final CustomerFeedbackRepository customerFeedbackRepository;
    private final QrDataRepository qrDataRepository;
    private final QrCodeValidationRepository qrCodeValidationRepository;
    private final QrCodeValidationQueryService qrCodeValidationQueryService;
    private final SmsService smsService;

    public List<QrStatusSummaryResponse> getStatusSummary(QrValidationSearchDto searchDto ) {
        if (Objects.nonNull(searchDto.getChartTypeEnum()) && Objects.nonNull(searchDto.getDashboardTypeEnum())) {
            if (searchDto.getDashboardTypeEnum().equals(DashboardTypeEnum.STATUS)) {
                if (searchDto.getChartTypeEnum().equals(ChartTypeEnum.QUARTER)) {
                    return qrCodeValidationQueryService.searchQrStatusSummaryQuarterly(searchDto);
                } else if (searchDto.getChartTypeEnum().equals(ChartTypeEnum.MONTH)) {
                    return qrCodeValidationQueryService.searchQrStatusSummaryMonthly(searchDto);
                }
            } else if (searchDto.getDashboardTypeEnum().equals(DashboardTypeEnum.METHOD)) {
                if (searchDto.getChartTypeEnum().equals(ChartTypeEnum.QUARTER)) {
                    return qrCodeValidationQueryService.searchQrMethodSummaryQuarterly(searchDto);
                } else if (searchDto.getChartTypeEnum().equals(ChartTypeEnum.MONTH)) {
                    return qrCodeValidationQueryService.searchQrMethodSummaryMonthly(searchDto);
                }
            }
        }
        return null;
    }

}
