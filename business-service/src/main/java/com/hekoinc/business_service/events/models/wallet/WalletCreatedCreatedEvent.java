package com.hekoinc.business_service.events.models.wallet;

import com.hekoinc.business_service.events.models.business.BusinessCreatedEvent;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@RequiredArgsConstructor
public class WalletCreatedCreatedEvent extends BusinessCreatedEvent {
    private String walletId;
    private String currency;
}
