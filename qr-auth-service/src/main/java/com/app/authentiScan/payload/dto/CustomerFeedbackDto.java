package com.app.authentiScan.payload.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerFeedbackDto {
    private Long id;
    private String name;
    @NotBlank
    private String mobileNumber;
    private String purchaseLocation;
    private String opinion;
    @NotBlank
    private String productUid;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
