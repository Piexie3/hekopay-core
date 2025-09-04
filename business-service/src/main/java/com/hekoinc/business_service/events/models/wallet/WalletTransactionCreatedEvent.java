package com.hekoinc.business_service.events.models.wallet;

import com.hekoinc.business_service.events.models.business.BusinessCreatedEvent;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@RequiredArgsConstructor
public class WalletTransactionCreatedEvent extends BusinessCreatedEvent {
    private String transactionId;
    private String walletId;
    private String transactionType;
    private BigDecimal amount;
    private String status;
}
