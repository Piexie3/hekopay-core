package com.hekoinc.merchant_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateMerchantRequest {
    private String merchantId;
    private String legalName;
    private String email;
    private LocalDateTime createdAt;
}
