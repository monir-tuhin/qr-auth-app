package com.app.authentiScan.models;

import com.app.authentiScan.enums.VerificationStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "qr_code_information_details")
public class QrData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "uid", length = 20, nullable = false, unique = true)
    private String uid;

    @Column(name = "sl", nullable = false, unique = true)
    private Integer sl;

    @Enumerated(EnumType.STRING)
    @Column(name = "qr_verification_scan_status", length = 20)
    private VerificationStatusEnum verificationScanStatusEnum = VerificationStatusEnum.PENDING;

    @Column(name = "qr_verification_count_scan")
    private Integer qrVerificationCountScan;

    @Column(name = "last_qr_verification_scan_at")
    private LocalDateTime lastQrVerificationScanAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "qr_verification_sms_status", length = 20)
    private VerificationStatusEnum verificationSmsStatusEnum = VerificationStatusEnum.PENDING;

    @Column(name = "brand_key", length = 10)
    private String brandKey;

    @Column(name = "qr_verification_count_sms")
    private Integer qrVerificationCountSms;

    @Column(name = "last_qr_verification_sms_at")
    private LocalDateTime lastQrVerificationSmsAt;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Transient
    private String scanUrl;
}
