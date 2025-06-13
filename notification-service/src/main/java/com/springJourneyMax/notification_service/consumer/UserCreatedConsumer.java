package com.springJourneyMax.notification_service.consumer;

import com.springJourneyMax.user_service.event.UserCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserCreatedConsumer {

    @KafkaListener(topics ="user-created-topic")
    public void listenUserCreatedEvent(UserCreatedEvent userCreatedEvent){
        log.info("listenUserCreatedEvent {}",userCreatedEvent);

    }
}
