package com.hekoinc.business_service.dto;

import com.hekoinc.business_service.utils.enums.BusinessStatus;
import com.hekoinc.business_service.utils.enums.BusinessType;
import com.hekoinc.business_service.utils.enums.KYBStatus;
import com.hekoinc.business_service.utils.enums.PaymentMethod;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class SearchBusinessRequest {
    private String name;
    private String location;
    private String industry;
    private String size;
    private String registrationStatus;
    private String region;
    private String sector;
    private String operationalStatus;
    private String legalName;
    private String tradingName;
    private String businessRegistrationNumber;
    private String kraPinNumber;
    private String email;
    private BusinessType businessType;
    private BusinessStatus businessStatus;
    private KYBStatus kybStatus;
    private String city;
    private String county;
    private String street;
    private String postalCode;
    private String country;
    private String phoneNumber;
    private String alternativeEmail;
    private String website;
    private Set<PaymentMethod> acceptedPaymentMethods;
    private LocalDateTime createdAfter;
    private LocalDateTime createdBefore;
    private LocalDateTime updatedAfter;
    private LocalDateTime updatedBefore;
    private LocalDateTime kybCompletedAfter;
    private LocalDateTime kybCompletedBefore;
    private Boolean hasOwners;
    private Integer minOwnersCount;
    private Integer maxOwnersCount;
    private Boolean isKybCompleted;
    private Boolean isFullyRegistered;

    public boolean hasAnySearchCriteria() {
        return StringUtils.hasText(name) ||
                StringUtils.hasText(location) ||
                StringUtils.hasText(legalName) ||
                StringUtils.hasText(tradingName) ||
                StringUtils.hasText(businessRegistrationNumber) ||
                StringUtils.hasText(kraPinNumber) ||
                StringUtils.hasText(email) ||
                businessType != null ||
                businessStatus != null ||
                kybStatus != null ||
                StringUtils.hasText(region) ||
                StringUtils.hasText(city) ||
                StringUtils.hasText(county) ||
                StringUtils.hasText(street) ||
                StringUtils.hasText(phoneNumber) ||
                acceptedPaymentMethods != null && !acceptedPaymentMethods.isEmpty() ||
                createdAfter != null ||
                createdBefore != null ||
                updatedAfter != null ||
                updatedBefore != null ||
                kybCompletedAfter != null ||
                kybCompletedBefore != null ||
                hasOwners != null ||
                minOwnersCount != null ||
                maxOwnersCount != null ||
                isKybCompleted != null ||
                isFullyRegistered != null;
    }
}