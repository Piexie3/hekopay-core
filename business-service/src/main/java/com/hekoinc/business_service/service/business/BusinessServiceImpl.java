package com.hekoinc.business_service.service.business;

import com.hekoinc.business_service.dto.*;
import com.hekoinc.business_service.events.models.business.BusinessCreatedEvent;
import com.hekoinc.business_service.events.publisher.BusinessEventPublisher;
import com.hekoinc.business_service.models.Business;
import com.hekoinc.business_service.models.BusinessAddress;
import com.hekoinc.business_service.models.BusinessProfile;
import com.hekoinc.business_service.models.ContactInformation;
import com.hekoinc.business_service.repository.BusinessRepository;
import com.hekoinc.business_service.utils.AppUtil;
import com.hekoinc.business_service.utils.enums.BusinessStatus;
import com.hekoinc.business_service.utils.mappers.BusinessMappers;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@RefreshScope
@RequiredArgsConstructor
@Slf4j
public class BusinessServiceImpl implements BusinessService {
    private final BusinessRepository repository;
    private final BusinessEventPublisher eventPublisher;
    private final KafkaTemplate<String, BusinessCreatedEvent> kafkaTemplate;

    @Override
    public void updateBusinessStatus(String businessId, BusinessStatus status) {
        Business business = repository.findByBusinessId(businessId)
                .orElseThrow(
                        () -> new NotFoundException("Verify your inputs, Business not Found ")
                );
        business.getProfile().setStatus(status);
        repository.save(business);
    }


    @Override
    public void createBusinessProfile(BusinessCreatedEvent event) {
        Optional<Business> existingBusiness = repository.findByBusinessId(event.getBusinessId());
        if (existingBusiness.isPresent()) {
            return;
        }
        Business business = Business.builder()
                .businessId(event.getBusinessId())
                .createdAt(event.getCreatedAt())
                .profile(BusinessProfile.builder()
                        .legalName(event.getLegalName())
                        .email(event.getEmail())
                        .businessType(event.getType())
                        .businessRegistrationNumber(event.getBusinessRegistrationNumber())
                        .build()
                )
                .build();
        repository.save(business);
    }

    @Override
    public BaseResponse getAllBusiness() {
        List<Business> businessList = repository.findAll();
        return BaseResponse.builder()
                .message("Successfully fetched")
                .code(200)
                .data(BusinessMappers.toBusinessResponseList(businessList))
                .build();
    }

    @Override
    public BaseResponse publishEvent(BusinessCreatedEvent req) {
        try {

            BusinessCreatedEvent event = new BusinessCreatedEvent();
            event.setBusinessId(req.getBusinessId());
            event.setEmail(req.getEmail());
            event.setLegalName(req.getLegalName());
            event.setCreatedAt(req.getCreatedAt());
            event.setBusinessRegistrationNumber(req.getBusinessRegistrationNumber());

            CompletableFuture<SendResult<String, BusinessCreatedEvent>> future =
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

    @Override
    public BaseResponse updateBusinessProfile(BusinessUpdateRequest request, String businessId) {
        repository.findByBusinessId(businessId).map(existBus -> {
                    if (existBus.getProfile() == null) {
                        existBus.setProfile(new BusinessProfile());
                    }
                    if (existBus.getContactInformation() == null) {
                        existBus.setContactInformation(new ContactInformation());
                    }
                    if (existBus.getBusinessAddress() == null) {
                        existBus.setBusinessAddress(new BusinessAddress());
                    }

                    BusinessProfile profile = existBus.getProfile();
                    ContactInformation info = existBus.getContactInformation();
                    BusinessAddress address = existBus.getBusinessAddress();
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
                    return repository.save(existBus);
                }
        ).orElseThrow(() -> new NotFoundException("Business not found"));

        return BaseResponse.builder()
                .code(200)
                .message("Business data updated successfully")
                .data(null)
                .build();
    }

    @Override
    public BaseResponse deleteBusiness(String businessId) {
        repository.findByBusinessId(businessId).map(bus -> {
            bus.getProfile().setStatus(BusinessStatus.ARCHIVED);
            return repository.save(bus);
        }).orElseThrow(() -> new NotFoundException("Business not found"));
        return BaseResponse.builder()
                .message("Business has been deleted. This account will not be accessed for a period of up-to 7 years")
                .code(200)
                .data(null)
                .build();
    }

    @Override
    public BaseResponse getBusinessByBusinessId(String businessId) {
        Business business = repository.findByBusinessId(businessId).orElseThrow(() -> new NotFoundException("Business not found"));
        return BaseResponse.builder()
                .message("Successfully fetched")
                .code(200)
                .data(BusinessMappers.toBusinessResponse(business))
                .build();
    }

    @Override
    public BaseResponse businessNearby(double latitude, double longitude, double radius) {
        return null;
    }

    @Override
    public BaseResponse searchBusiness(SearchBusinessRequest request, int page, int size, String sortBy, String sortDirection) {
        Pageable pageable = createPageable(page, size, sortBy, sortDirection);

        Page<BusinessResponse> result = repository.findAll(pageable)
                .map(this::convertToDTO);
        return BaseResponse.builder()
                .message("Successfully fetched")
                .code(200)
                .data(result).build();
    }

    private Pageable createPageable(int page, int size, String sortBy, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), mapSortField(sortBy));
        return PageRequest.of(page, size, sort);
    }

    private String mapSortField(String sortBy) {
        return switch (sortBy) {
            case "tradingName" -> "profile.tradingName";
            case "businessType" -> "profile.businessType";
            case "status" -> "profile.status";
            case "region" -> "businessAddress.region";
            case "city" -> "businessAddress.city";
            case "createdAt" -> "createdAt";
            case "updatedAt" -> "updatedAt";
            default -> "profile.legalName";
        };
    }

    private BusinessResponse convertToDTO(Business business) {
        if (business.getProfile() == null) {
            business.setProfile(new BusinessProfile());
        }
        if (business.getContactInformation() == null) {
            business.setContactInformation(new ContactInformation());
        }
        if (business.getBusinessAddress() == null) {
            business.setBusinessAddress(new BusinessAddress());
        }

        BusinessProfile profile = business.getProfile();
        BusinessAddress address = business.getBusinessAddress();
        ContactInformation contact = business.getContactInformation();


        return BusinessResponse.builder()
                .businessId(business.getBusinessId())
                .legalName(profile.getLegalName())
                .tradingName(profile.getTradingName())
                .businessRegistrationNumber(profile.getBusinessRegistrationNumber())
                .email(profile.getEmail())
                .businessType(profile.getBusinessType())
                .status(profile.getStatus())
                .kybStatus(profile.getKybStatus())
                .county(address.getRegion())
                .city(address.getCity())
                .street(address.getStreet())
                .phone(contact.getPrimaryPhone())
                .alternativeEmail(contact.getSecondaryEmail())
                .createdAt(business.getCreatedAt())
                .updatedAt(business.getUpdatedAt())
                .build();
    }

}
