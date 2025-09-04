package com.hekoinc.business_service.events.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hekoinc.business_service.events.models.business.BusinessCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class BusinessEventPublisher {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void publishBusinessEvents(BusinessCreatedEvent event, String topic){
        try {
            String eventJson = objectMapper.writeValueAsString(event);
            kafkaTemplate.send(topic, event.getBusinessId(), eventJson);
            log.info("Published business event: {}", topic);
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize business event: {}", e.getMessage());
        } catch (Exception e) {
            log.error("Failed to publish business event: {}", e.getMessage());
        }
    }
    public void publishSystemEvent(String eventType, String message) {
        try {
            kafkaTemplate.send("system-events", eventType, message);
            log.info("Published system event: {}", eventType);
        } catch (Exception e) {
            log.error("Failed to publish system event: {}", e.getMessage());
        }
    }
}
