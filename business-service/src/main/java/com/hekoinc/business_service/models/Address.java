package com.hekoinc.business_service.models;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private String street1;
    private String street2;
    private String city;
    private String state;
    private String postalCode;
    private String country;
}
