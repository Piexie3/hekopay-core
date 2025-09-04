package com.hekoinc.merchant_service.dtos;

import com.hekoinc.merchant_service.utils.enums.MerchantType;
import com.hekoinc.merchant_service.utils.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MerchantUpdateRequest {
    private String tradingName;
    private Set<PaymentMethod> acceptedPaymentMethods;
    private MerchantType businessType;
    private String primaryEmail;
    private String secondaryEmail;
    private String primaryPhone;
    private String secondaryPhone;
    private String website;
    private String customerSupportEmail;
    private String customerSupportPhone;
    private String street;
    private String city;
    private String region;
    private String postalCode;
    private String country;
    private String county;
}
