package com.app.authentiScan.payload.dto.search;

import com.app.authentiScan.enums.VerificationStatusEnum;
import com.app.authentiScan.payload.dto.SearchDto;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QrVerificationHistorySearchDto extends SearchDto {
    private String uid;
    private Integer sl;
    private VerificationStatusEnum verificationStatusEnum;
    private LocalDateTime lastQrVerification;
    private String searchTerm;

}
