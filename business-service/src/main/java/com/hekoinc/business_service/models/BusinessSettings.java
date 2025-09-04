package com.hekoinc.business_service.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BusinessSettings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "business_id")
    private Business business;

    @Column(precision = 19, scale = 2)
    private BigDecimal dailyTransactionLimit;

    @Column(precision = 19, scale = 2)
    private BigDecimal monthlyTransactionLimit;

    @Column(precision = 19, scale = 2)
    private BigDecimal singleTransactionLimit;

    private boolean allowInternationalTransactions;
    private boolean requireTwoFactorAuth;

    private boolean emailNotifications;
    private boolean smsNotifications;
    private String notificationEmail;

    @Enumerated(EnumType.STRING)
    private SettlementFrequency settlementFrequency;

    @Column(precision = 5, scale = 4)
    private BigDecimal processingFeePercentage;

    @Column(precision = 19, scale = 2)
    private BigDecimal fixedProcessingFee;

    private boolean autoSettlement;
    private Integer settlementDelayDays;

    // Risk Settings
    @Column(precision = 3, scale = 2)
    private BigDecimal riskThreshold;

    private boolean enableFraudDetection = true;
    private boolean enableVelocityChecks;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public enum SettlementFrequency {
        DAILY, WEEKLY, MONTHLY, ON_DEMAND
    }
}
