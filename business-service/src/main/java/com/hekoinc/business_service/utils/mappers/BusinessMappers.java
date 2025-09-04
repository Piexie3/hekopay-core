package com.hekoinc.business_service.utils.mappers;

import com.hekoinc.business_service.dto.BusinessResponse;
import com.hekoinc.business_service.models.Business;
import com.hekoinc.business_service.utils.enums.topics.BusinessTopic;

import java.util.List;

public class BusinessMappers {
    public static BusinessResponse toBusinessResponse(Business business){
        return BusinessResponse.builder()
                .businessId(business.getBusinessId())
                .businessType(business.getProfile().getBusinessType())
                .contactInformation(business.getContactInformation())
                .createdAt(business.getCreatedAt())
                .kybStatus(business.getProfile().getKybStatus())
                .legalName(business.getProfile().getLegalName())
                .status(business.getProfile().getStatus())
                .tradingName(business.getProfile().getTradingName())
                .build();
    }
    public static List<BusinessResponse> toBusinessResponseList(List<Business> businessList){
        return businessList.stream().map(BusinessMappers::toBusinessResponse).toList();
    }
}
