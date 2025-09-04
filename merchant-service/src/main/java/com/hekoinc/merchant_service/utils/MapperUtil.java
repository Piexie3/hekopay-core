package com.hekoinc.merchant_service.utils;

import com.hekoinc.merchant_service.dtos.MerchantDto;
import com.hekoinc.merchant_service.models.Merchant;

public class MapperUtil {
    public static MerchantDto toMerchantDto(Merchant merchant) {
        return MerchantDto.builder().build();
    }
}
