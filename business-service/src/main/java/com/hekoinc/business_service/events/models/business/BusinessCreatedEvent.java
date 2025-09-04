package com.hekoinc.business_service.events.models.business;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.joda.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.joda.ser.LocalDateTimeSerializer;
import com.hekoinc.business_service.events.models.wallet.WalletCreatedCreatedEvent;
import com.hekoinc.business_service.utils.enums.BusinessType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusinessCreatedEvent {
    private String businessId;
    private String legalName;
    private String email;
    private String businessRegistrationNumber;
    private BusinessType type;
    private LocalDateTime createdAt;
}
