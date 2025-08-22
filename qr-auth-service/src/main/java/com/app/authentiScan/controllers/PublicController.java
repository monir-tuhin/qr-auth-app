package com.app.authentiScan.controllers;

import com.app.authentiScan.payload.dto.CustomerFeedbackDto;
import com.app.authentiScan.payload.request.QrVerificationSmsRequest;
import com.app.authentiScan.payload.response.QrCodeVerificationResponse;
import com.app.authentiScan.service.CustomerFeedbackService;
import com.app.authentiScan.utils.ApiResponseFactory;
import com.app.authentiScan.utils.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/public")
@RequiredArgsConstructor
public class PublicController {

    private final CustomerFeedbackService customerFeedbackService;
    private final ApiResponseFactory apiResponseFactory;

    @PostMapping("/customer-feedback")
    public ResponseEntity<?> save(@RequestBody CustomerFeedbackDto dto) {
        customerFeedbackService.save(dto);
        return apiResponseFactory.saveResponse("Successfully submitted");
    }

    @PutMapping( "/qr-code-verification-scan")
    public ResponseEntity<ApiResponse<QrCodeVerificationResponse>> qrCodeVerificationScan(
            @RequestParam(name = "uid") String uid){
        return apiResponseFactory.getResponse(customerFeedbackService.qrCodeVerificationScan(uid));
    }

    @PutMapping( "/qr-code-verification-sms")
    public ResponseEntity<ApiResponse<QrCodeVerificationResponse>> qrCodeVerificationSms(
            @Valid @RequestBody QrVerificationSmsRequest request){
        return apiResponseFactory.getResponse(customerFeedbackService.qrCodeVerificationSms(request));
    }

}
