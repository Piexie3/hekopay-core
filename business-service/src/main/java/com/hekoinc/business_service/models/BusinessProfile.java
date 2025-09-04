package com.hekoinc.business_service.models;

import com.hekoinc.business_service.utils.enums.BusinessStatus;
import com.hekoinc.business_service.utils.enums.BusinessType;
import com.hekoinc.business_service.utils.enums.KYBStatus;
import com.hekoinc.business_service.utils.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BusinessProfile {
    @Column(nullable = false)
    private String legalName;
    private String tradingName;
    private String businessRegistrationNumber;
    private String kraPinNumber;
    private String email;
    @Enumerated(EnumType.STRING)
    private BusinessType businessType;
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private BusinessStatus status = BusinessStatus.PENDING_VERIFICATION;
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<PaymentMethod> acceptedPaymentMethods;
    @Column(name = "kyb_status")
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private KYBStatus kybStatus = KYBStatus.NOT_STARTED;

    private LocalDateTime kybCompletedAt;
}
