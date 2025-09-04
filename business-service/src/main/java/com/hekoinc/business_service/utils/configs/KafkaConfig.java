package com.hekoinc.business_service.utils.configs;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hekoinc.business_service.utils.enums.topics.BusinessTopic;
import com.hekoinc.business_service.utils.enums.topics.WalletTopics;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {
    @Bean
    public KafkaAdmin.NewTopics topics(){
        Map<String,String> commonConfigs = new HashMap<>();
        commonConfigs.put(TopicConfig.CLEANUP_POLICY_CONFIG, TopicConfig.CLEANUP_POLICY_DELETE);
        commonConfigs.put(TopicConfig.RETENTION_MS_CONFIG,"604800000");
        return new KafkaAdmin.NewTopics(
                new NewTopic(BusinessTopic.BUSINESS_ARCHIVED.name(), 3, (short) 3).configs(commonConfigs),
                new NewTopic(BusinessTopic.BUSINESS_STATUS_UPDATE.name(),  3, (short) 3).configs(commonConfigs),
                new NewTopic(BusinessTopic.BUSINESS_ACCOUNT_UPDATE.name(), 3, (short) 3).configs(commonConfigs),
                new NewTopic(BusinessTopic.BUSINESS_SIGN_UP.name(), 3, (short) 3).configs(commonConfigs),
                new NewTopic(WalletTopics.WALLET_FUNDED.name(), 3, (short) 3).configs(commonConfigs),
                new NewTopic(WalletTopics.WALLET_BALANCE_UPDATE.name(), 3, (short) 3).configs(commonConfigs),
                new NewTopic(WalletTopics.WALLET_STATUS_CHANGE.name(), 3, (short) 3).configs(commonConfigs),
                new NewTopic(WalletTopics.WALLET_UPDATE.name(), 3, (short) 3).configs(commonConfigs)

        );
    }

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }
}
