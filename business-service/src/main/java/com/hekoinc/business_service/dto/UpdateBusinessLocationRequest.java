package com.hekoinc.business_service.dto;

import lombok.Data;

@Data
public class UpdateBusinessLocationRequest {
    private String city;
    private String state;
    private String postalCode;
    private double latitude;
    private double longitude;
}
