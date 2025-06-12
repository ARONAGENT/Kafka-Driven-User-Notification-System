package com.springJourneyMax.user_service.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

    @Value("${kafka.topic.user-random-topic}")
    private String KAFKA_RANDOM_USER_TOPIC;

    private final KafkaTemplate<String,String> kafkaTemplate;
    @PostMapping("/{message}")
    public ResponseEntity<String> sendMsg(@PathVariable String message){
        kafkaTemplate.send(KAFKA_RANDOM_USER_TOPIC,message);
        return ResponseEntity.ok("Message Send");
    }

}
