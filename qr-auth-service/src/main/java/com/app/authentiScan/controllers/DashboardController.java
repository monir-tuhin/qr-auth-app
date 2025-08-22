package com.app.authentiScan.controllers;

import com.app.authentiScan.payload.dto.search.QrValidationSearchDto;
import com.app.authentiScan.payload.response.QrStatusSummaryResponse;
import com.app.authentiScan.service.DashboardService;
import com.app.authentiScan.utils.ApiResponseFactory;
import com.app.authentiScan.utils.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/dashboard")
@RestController
@Slf4j
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;
    private final ApiResponseFactory apiResponseFactory;

    @PostMapping("/qr-status-summary")
    public ResponseEntity<ApiResponse<List<QrStatusSummaryResponse>>> getStatusSummary(@RequestBody QrValidationSearchDto searchDto) {
        return apiResponseFactory.getResponse(dashboardService.getStatusSummary(searchDto));
    }

}
