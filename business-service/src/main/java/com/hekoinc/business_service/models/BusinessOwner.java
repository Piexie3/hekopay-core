package com.hekoinc.business_service.models;

import com.hekoinc.business_service.utils.enums.OwnerType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BusinessOwner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "business_id")
    private Business business;

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private String ssn; // Should be encrypted
    private String nationalId;

    @Embedded
    private Address address;

    @Column(precision = 5, scale = 2)
    private BigDecimal ownershipPercentage;

    private String title;

    @Enumerated(EnumType.STRING)
    private OwnerType ownerType;

    private boolean isSignatory;
    private boolean isPoliticallyExposed;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}