package com.hekoinc.merchant_service.service;

import com.hekoinc.merchant_service.dtos.*;
import com.hekoinc.merchant_service.models.ContactInformation;
import com.hekoinc.merchant_service.models.Merchant;
import com.hekoinc.merchant_service.models.MerchantAddress;
import com.hekoinc.merchant_service.models.MerchantProfile;
import com.hekoinc.merchant_service.repository.MerchantRepository;
import com.hekoinc.merchant_service.utils.AppUtil;
import com.hekoinc.merchant_service.utils.Constants;
import com.hekoinc.merchant_service.utils.MapperUtil;
import com.hekoinc.merchant_service.utils.enums.MerchantStatus;
import jakarta.websocket.SendResult;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class MerchantServiceImpl implements MerchantService {
    private final MerchantRepository merchantRepository;
    private final KafkaTemplate<String, CreateMerchantRequest> kafkaTemplate;


    @Override
    public void createMerchant(CreateMerchantRequest request) {
        Optional<Merchant> existingBusiness = merchantRepository.findByMerchantId(request.getMerchantId());
        if (existingBusiness.isPresent()) {
            return;
        }
        Merchant business = Merchant.builder()
                .merchantId(request.getMerchantId())
                .createdAt(request.getCreatedAt())
                .profile(MerchantProfile.builder()
                        .legalName(request.getLegalName())
                        .email(request.getEmail())
                        .build()
                )
                .build();
        merchantRepository.save(business);
    }

    @Override
    public BaseResponse getAllMerchants() {
        List<MerchantDto> merchants = merchantRepository.findAll().stream().map(MapperUtil::toMerchantDto).toList();
        return BaseResponse.builder()
                .code(HttpStatus.OK.value())
                .message(Constants.Success)
                .data(merchants)
                .build();
    }

    @Override
    public BaseResponse searchMerchant(SearchMerchantRequest request, int page, int size, String sortBy, String sortDirection) {
        return null;
    }

    @Override
    public BaseResponse updateMerchantProfile(MerchantUpdateRequest request, String merchantId) {
        merchantRepository.findByMerchantId(merchantId).map(merchant -> {

                    if (merchant.getProfile() == null) {
                        merchant.setProfile(new MerchantProfile());
                    }
                    if (merchant.getContactInformation() == null) {
                        merchant.setContactInformation(new ContactInformation());
                    }
                    if (merchant.getMerchantAddress() == null) {
                        merchant.setMerchantAddress(new MerchantAddress());
                    }
                    MerchantProfile profile = merchant.getProfile();
                    ContactInformation info = merchant.getContactInformation();
                    MerchantAddress address = merchant.getMerchantAddress();

            AppUtil.updateField(profile::setTradingName, request.getTradingName());
            AppUtil.updateField(profile::setAcceptedPaymentMethods, request.getAcceptedPaymentMethods());
            AppUtil.updateField(profile::setBusinessType, request.getBusinessType());
            AppUtil.updateField(info::setPrimaryEmail, request.getPrimaryEmail());
            AppUtil.updateField(info::setSecondaryEmail, request.getSecondaryEmail());
            AppUtil.updateField(info::setPrimaryPhone, request.getPrimaryPhone());
            AppUtil.updateField(info::setSecondaryPhone, request.getSecondaryPhone());
            AppUtil.updateField(info::setCustomerSupportEmail, request.getCustomerSupportEmail());
            AppUtil.updateField(info::setCustomerSupportPhone, request.getCustomerSupportPhone());
            AppUtil.updateField(info::setWebsite, request.getWebsite());
            AppUtil.updateField(address::setStreet, request.getStreet());
            AppUtil.updateField(address::setCity, request.getCity());
            AppUtil.updateField(address::setRegion, request.getRegion());
            AppUtil.updateField(address::setCountry, request.getCountry());
            AppUtil.updateField(address::setPostalCode, request.getPostalCode());
            AppUtil.updateField(address::setCounty, request.getCounty());
            return merchantRepository.save(merchant);
                }

        ).orElseThrow(() -> new NotFoundException("Merchant not found"));
        return BaseResponse.builder()
                .code(200)
                .message("Merchant data updated successfully")
                .data(null)
                .build();
    }

    @Override
    public BaseResponse deleteMerchant(String merchantId) {
        merchantRepository.findByMerchantId(merchantId).map(merchant -> {
            merchant.getProfile().setStatus(MerchantStatus.ARCHIVED);
            return merchantRepository.save(merchant);
        }).orElseThrow(() -> new NotFoundException("Merchant not found"));
        return BaseResponse.builder()
                .message("Merchant has been deleted. This account will not be accessed for a period of up-to 7 years")
                .code(200)
                .data(null)
                .build();
    }

    @Override
    public BaseResponse nearbyMerchants(double lat, double lng, double rad) {
        return null;
    }

    @Override
    public BaseResponse createMerchantEvent(CreateMerchantRequest req) {
        try {

            CreateMerchantRequest event = new CreateMerchantRequest();
            event.setMerchantId(req.getMerchantId());
            event.setEmail(req.getEmail());
            event.setLegalName(req.getLegalName());
            event.setCreatedAt(req.getCreatedAt());
            CompletableFuture<SendResult<String, CreateMerchantRequest>> future =
                    kafkaTemplate.send("BUSINESS_SIGN_UP", event.getBusinessId(), event);

            future.thenAccept(result -> {
                //handling successful event publication

            }).exceptionally(throwable -> {
                //handling error event publication
                return null;
            });
            return BaseResponse.builder()
                    .code(200)
                    .message("event sent successfully")
                    .data(null).build();

        } catch (Exception e) {
            throw new RuntimeException("Failed to publish business created event", e);
        }
    }
}
