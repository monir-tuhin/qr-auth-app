package com.app.authentiScan.payload.request;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QrVerificationSmsRequest {
    @NotBlank(message = "UID is required")
    private String uid;
    @NotBlank(message = "Mobile number is required")
    private String mobileNumber;
    private String message;
}
