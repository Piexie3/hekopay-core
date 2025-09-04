package com.hekoinc.business_service.events.listeners;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hekoinc.business_service.events.models.business.BusinessCreatedEvent;
import com.hekoinc.business_service.service.business.BusinessService;
import com.hekoinc.business_service.utils.enums.topics.BusinessTopic;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class BusinessCreatedListener {
    private final BusinessService businessService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics =  "BUSINESS_SIGN_UP",groupId = "${spring.kafka.consumer.group-id}")
    public void handleBusinessCreationEvent(
            @Payload BusinessCreatedEvent event,
            Acknowledgment acknowledgment
    ) {
        try {
            log.debug("Raw payload: {}", event);

            businessService.createBusinessProfile(event);
            acknowledgment.acknowledge();
            log.info("Successfully processed UserCreated event for user {}", event.getBusinessId());
        } catch (Exception e) {
            log.error("Failed to process UserCreated event for user {}: {}",
                    event, e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
