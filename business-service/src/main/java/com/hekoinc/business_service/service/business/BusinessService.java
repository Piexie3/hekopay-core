package com.hekoinc.business_service.service.business;

import com.hekoinc.business_service.dto.*;
import com.hekoinc.business_service.events.models.business.BusinessCreatedEvent;
import com.hekoinc.business_service.utils.enums.BusinessStatus;

public interface BusinessService {
    void updateBusinessStatus(String businessId, BusinessStatus status);
    void createBusinessProfile(BusinessCreatedEvent event);

    BaseResponse getAllBusiness();

    BaseResponse publishEvent(BusinessCreatedEvent event);

    BaseResponse updateBusinessProfile(BusinessUpdateRequest request,String businessId);
    BaseResponse deleteBusiness(String businessId);
    BaseResponse getBusinessByBusinessId(String businessId);
    BaseResponse searchBusiness(SearchBusinessRequest request,int page,int size,String sortBy,String direction);
    BaseResponse businessNearby(double latitude,double longitude,double radius);
}
