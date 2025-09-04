package com.hekoinc.business_service.models;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusinessAddress {
    private String street;
    private String city;
    private String region;
    private String county;
    private String postalCode;
    private String country;
    private boolean sameAsLegal;
}