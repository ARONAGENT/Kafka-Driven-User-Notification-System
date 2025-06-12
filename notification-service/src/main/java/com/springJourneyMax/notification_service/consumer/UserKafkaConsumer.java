package com.springJourneyMax.notification_service.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserKafkaConsumer {

    @KafkaListener(topics = "user-random-topic")
    public void listenUserRandomTopic1(String message){
        // client 1 listen topic user-random-topic
        log.info("listenUserRandomTopic1 : {}",message);
    }

    @KafkaListener(topics = "user-random-topic")
    public void listenUserRandomTopic2(String message){
        // client 2 listen topic user-random-topic
        log.info("listenUserRandomTopic2: {}",message);
    }

    @KafkaListener(topics = "user-random-topic")
    public void listenUserRandomTopic3(String message){
        // client 3 listen topic user-random-topic
        log.info("listenUserRandomTopic3: {}",message);
    }
}
