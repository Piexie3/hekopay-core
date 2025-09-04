package com.hekoinc.merchant_service.repository;

import com.hekoinc.merchant_service.models.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MerchantRepository extends JpaRepository<Merchant,Long> {

    @Query("SELECT * FROM merchant m WHERE m.merchant_id = :merchantId")
    Optional<Merchant> findByMerchantId(String merchantId);
}
