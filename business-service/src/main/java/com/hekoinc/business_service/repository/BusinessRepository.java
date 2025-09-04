package com.hekoinc.business_service.repository;

import com.hekoinc.business_service.models.Business;
import com.hekoinc.business_service.utils.enums.BusinessStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BusinessRepository extends JpaRepository<Business, Long> {

    Optional<Business> findByBusinessId(String businessId);

    @Query("SELECT b FROM Business b WHERE b.profile.businessRegistrationNumber = :registrationNumber")
    Optional<Business> findByBusinessRegistrationNumber(String registrationNumber);

    @Query("SELECT b FROM Business b WHERE b.profile.status = :status")
    List<Business> findByStatus(BusinessStatus status);

    @Query("SELECT b FROM Business b WHERE b.contactInformation.primaryEmail = :email")
    Optional<Business> findByEmail(@Param("email") String email);

    @Query("SELECT b FROM Business b WHERE b.profile.kybStatus = 'PENDING_REVIEW'")
    List<Business> findBusinessesPendingKybReview();

//    @Query("SELECT b FROM Business b JOIN b.wallet w WHERE w.walletId = :walletId")
//    Optional<Business> findByWalletId(@Param("walletId") String walletId);
}