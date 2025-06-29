package com.springJourneyMax.user_service.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {

    @Value("${kafka.topic.user-random-topic}")
    private  String KAFKA_RANDOM_USER_TOPIC;

    @Value("${kafka.topic.user-created-topic}")
    private  String KAFKA_CREATED_USER_TOPIC;

    @Bean
    public NewTopic userRandomTopic(){
        return new NewTopic(KAFKA_RANDOM_USER_TOPIC,3, (short) 1);
    }

    @Bean
    public NewTopic userCreatedTopic(){
        return new NewTopic(KAFKA_CREATED_USER_TOPIC,3, (short) 1);
    }
}
