package com.app.authentiScan.payload.request;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ForgotPasswordRequest {
    @NotBlank
    private String email;
    private String mobile;
}
