package com.hekoinc.business_service.controllers;

import com.hekoinc.business_service.dto.BaseResponse;
import com.hekoinc.business_service.dto.BusinessUpdateRequest;
import com.hekoinc.business_service.dto.SearchBusinessRequest;
import com.hekoinc.business_service.events.models.business.BusinessCreatedEvent;
import com.hekoinc.business_service.service.business.BusinessService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/bs")
public class BusinessControllers {
    private final BusinessService businessService;

    @GetMapping("/all")
    public ResponseEntity<BaseResponse> getAllBusinesses() {
        return ResponseEntity.ok(businessService.getAllBusiness());
    }

    @PostMapping("/publishEvent")
    public ResponseEntity<BaseResponse> publishEvent(@RequestBody BusinessCreatedEvent event) {
        return ResponseEntity.ok(businessService.publishEvent(event));
    }

    @GetMapping("/search")
    public ResponseEntity<BaseResponse> searchBusiness(
            @RequestBody SearchBusinessRequest request,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection
    ) {
        return ResponseEntity.ok(businessService.searchBusiness(request, page, size, sortBy, sortDirection));
    }

    @PatchMapping("/update/{businessId}")
    public ResponseEntity<BaseResponse> updateBusiness(@Valid @RequestBody  BusinessUpdateRequest request, @PathVariable String businessId){
        return ResponseEntity.ok(businessService.updateBusinessProfile(request,businessId));
    }

    @DeleteMapping("/delete/{businessId}")
    public ResponseEntity<BaseResponse> deleteBusiness(@PathVariable String businessId){
        return ResponseEntity.ok(businessService.deleteBusiness(businessId));
    }

    @GetMapping("/{businessId}")
    public ResponseEntity<BaseResponse> getById(@PathVariable String businessId){
        return ResponseEntity.ok(businessService.getBusinessByBusinessId(businessId));
    }

    @GetMapping("/near-by")
    public ResponseEntity<BaseResponse> getNearbyBusiness(@RequestParam double lat,@RequestParam double lng,@RequestParam(defaultValue = "10.00") double rad){
        return ResponseEntity.ok(businessService.businessNearby(lat, lng, rad));
    }
}
