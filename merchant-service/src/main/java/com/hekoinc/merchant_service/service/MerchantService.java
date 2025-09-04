package com.hekoinc.merchant_service.service;

import com.hekoinc.merchant_service.dtos.BaseResponse;
import com.hekoinc.merchant_service.dtos.CreateMerchantRequest;
import com.hekoinc.merchant_service.dtos.MerchantUpdateRequest;
import com.hekoinc.merchant_service.dtos.SearchMerchantRequest;
import jakarta.validation.Valid;

public interface MerchantService {
    void createMerchant(CreateMerchantRequest request);

    BaseResponse getAllMerchants();

    BaseResponse searchMerchant(SearchMerchantRequest request, int page, int size, String sortBy, String sortDirection);

    BaseResponse updateMerchantProfile(@Valid MerchantUpdateRequest request, String businessId);

    BaseResponse deleteMerchant(String businessId);

    BaseResponse nearbyMerchants(double lat, double lng, double rad);

    BaseResponse createMerchantEvent(CreateMerchantRequest event);
}
