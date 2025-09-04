package com.hekoinc.business_service.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(indexes = {
        @Index(name = "idx_business_legal_name", columnList = "legal_name"),
        @Index(name = "idx_business_trading_name", columnList = "trading_name"),
        @Index(name = "idx_business_type", columnList = "business_type"),
        @Index(name = "idx_business_status", columnList = "status"),
        @Index(name = "idx_business_registration_number", columnList = "business_registration_number"),
        @Index(name = "idx_kra_pin_number", columnList = "kra_pin_number"),
        @Index(name = "idx_kyb_status", columnList = "kyb_status"),
        @Index(name = "idx_business_type_status", columnList = "business_type, status"),
        @Index(name = "idx_region_city", columnList = "region, city"),
        @Index(name = "idx_status_kyb", columnList = "status, kyb_status"),
        @Index(name = "idx_business_county", columnList = "county"),
        @Index(name = "idx_business_email", columnList = "email"),
})
public class Business {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String businessId;

    @Embedded
    private BusinessProfile profile;
    @Embedded
    private BusinessAddress businessAddress;
    @Embedded
    private ContactInformation contactInformation;
    @OneToMany(mappedBy = "business", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BusinessOwner> owners;
    @OneToOne(mappedBy = "business", cascade = CascadeType.ALL)
    private BusinessSettings settings;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
