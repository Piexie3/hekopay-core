package com.hekoinc.merchant_service.models;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MerchantAddress {
    private String street;
    private String city;
    private String region;
    private String county;
    private String postalCode;
    private String country;
}
