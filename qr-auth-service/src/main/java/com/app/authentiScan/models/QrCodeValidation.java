package com.app.authentiScan.models;

import com.app.authentiScan.enums.*;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "qr_code_validation")
public class QrCodeValidation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "uid", length = 20, nullable = false)
    private String uid;

    @Enumerated(EnumType.STRING)
    @Column(name = "validation_methods", length = 20, nullable = false)
    private ValidationMethodsEnum validationMethodsEnum;

    @Enumerated(EnumType.STRING)
    @Column(name = "validation_status", length = 20, nullable = false)
    private ValidationStatusEnum validationStatusEnum;

    @Column(name = "mobile_number", length = 20)
    private String mobileNumber;

    @Column(name = "validation_date", nullable = false)
    private LocalDateTime validationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "week", length = 20, nullable = false)
    private WeekEnum weekEnum;

    @Column(name = "month", length = 20, nullable = false)
    private String month;

    @Enumerated(EnumType.STRING)
    @Column(name = "quarter", length = 20, nullable = false)
    private QuarterEnum quarterEnum;

    @Column(name = "year", nullable = false)
    private Integer year;

}
