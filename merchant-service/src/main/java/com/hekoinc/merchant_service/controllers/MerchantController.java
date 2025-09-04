package com.hekoinc.merchant_service.controllers;

import com.hekoinc.merchant_service.dtos.BaseResponse;
import com.hekoinc.merchant_service.dtos.CreateMerchantRequest;
import com.hekoinc.merchant_service.dtos.MerchantUpdateRequest;
import com.hekoinc.merchant_service.dtos.SearchMerchantRequest;
import com.hekoinc.merchant_service.service.MerchantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/ms")
public class MerchantController {
    private final MerchantService merchantService;

    @GetMapping("/all")
    public ResponseEntity<BaseResponse> getAllBusinesses() {
        return ResponseEntity.ok(merchantService.getAllMerchants());
    }

    @GetMapping("/search")
    public ResponseEntity<BaseResponse> searchBusiness(
            @RequestBody SearchMerchantRequest request,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection
    ) {
        return ResponseEntity.ok(merchantService.searchMerchant(request, page, size, sortBy, sortDirection));
    }
    @PatchMapping("/update/{merchantId}")
    public ResponseEntity<BaseResponse> updateBusiness(@Valid @RequestBody MerchantUpdateRequest request, @PathVariable String merchantId){
        return ResponseEntity.ok(merchantService.updateMerchantProfile(request,merchantId));
    }
    @DeleteMapping("/delete/{merchantId}")
    public ResponseEntity<BaseResponse> deleteBusiness(@PathVariable String merchantId){
        return ResponseEntity.ok(merchantService.deleteMerchant(merchantId));
    }
    @GetMapping("/near-by")
    public ResponseEntity<BaseResponse> getNearbyBusiness(@RequestParam double lat,@RequestParam double lng,@RequestParam(defaultValue = "10.00") double rad){
        return ResponseEntity.ok(merchantService.nearbyMerchants(lat, lng, rad));
    }
    @PostMapping("/create-merchant-event")
    public ResponseEntity<BaseResponse> publishEvent(@RequestBody CreateMerchantRequest event) {
        return ResponseEntity.ok(merchantService.createMerchantEvent(event));
    }
}
