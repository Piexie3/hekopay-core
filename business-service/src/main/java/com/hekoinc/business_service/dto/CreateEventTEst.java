package com.hekoinc.business_service.dto;

import com.hekoinc.business_service.utils.enums.BusinessType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateEventTEst {
    private String eventId;
    private String businessId;
    private String legalName;
    private String email;
    private String businessRegistrationNumber;
    private BusinessType type;
    private LocalDateTime createdAt;
}
