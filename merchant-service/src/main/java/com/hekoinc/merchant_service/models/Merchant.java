package com.hekoinc.merchant_service.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Merchant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String merchantId;

    @Embedded
    private MerchantProfile profile;
    @Embedded
    private ContactInformation contactInformation;
    @Embedded
    private MerchantAddress merchantAddress;
    @OneToOne(mappedBy = "merchant", cascade = CascadeType.ALL)
    private MerchantSettings settings;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
