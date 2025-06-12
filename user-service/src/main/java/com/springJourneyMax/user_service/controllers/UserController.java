package com.springJourneyMax.user_service.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    private final KafkaTemplate<String,String> kafkaTemplate;
    @PostMapping("/{message}")
    public ResponseEntity<String> sendMsg(@PathVariable String message){
        kafkaTemplate.send("user-random-topic",message);
        return ResponseEntity.ok("Message Send");
    }

}
