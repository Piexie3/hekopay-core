package com.hekoinc.business_service.dto;

import com.hekoinc.business_service.models.ContactInformation;
import com.hekoinc.business_service.utils.enums.AccountStatus;
import com.hekoinc.business_service.utils.enums.BusinessStatus;
import com.hekoinc.business_service.utils.enums.BusinessType;
import com.hekoinc.business_service.utils.enums.KYBStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BusinessResponse {
    private String businessId;
    private String legalName;
    private String tradingName;
    private BusinessType businessType;
    private BusinessStatus status;
    private KYBStatus kybStatus;
    private ContactInformation contactInformation;
    private LocalDateTime createdAt;
    private String businessRegistrationNumber;
    private String email;
    private String alternativeEmail;
    private String city;
    private String county;
    private String street;
    private String phone;
    private LocalDateTime updatedAt;
}
