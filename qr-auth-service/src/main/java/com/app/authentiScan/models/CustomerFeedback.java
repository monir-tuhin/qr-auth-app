package com.app.authentiScan.models;

import lombok.*;
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
@Table(name = "customer_feedback")
public class CustomerFeedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "mobile_number", length = 20, nullable = false)
    private String mobileNumber;

    @Column(name = "purchase_location", length = 100)
    private String purchaseLocation;

    @Column(name = "opinion", length = 200)
    private String opinion;

    @Column(name = "product_uid", length = 50, nullable = false, unique = true)
    private String productUid;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}