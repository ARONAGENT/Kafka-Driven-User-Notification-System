package com.springJourneyMax.user_service.services;

import com.springJourneyMax.events.UserCreatedEvent;
import com.springJourneyMax.user_service.dtos.UserDto;
import com.springJourneyMax.user_service.entities.UserEntity;
import com.springJourneyMax.user_service.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServices {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final KafkaTemplate<Long, UserCreatedEvent> kafkaTemplate;

    @Value("${kafka.topic.user-created-topic}")
    private String KAFKA_CREATED_USER_TOPIC;

    public void createUser(UserDto userDto){
       UserEntity userEntity= modelMapper.map(userDto, UserEntity.class);
       UserEntity userEntity1=  userRepository.save(userEntity);

       UserCreatedEvent userCreatedEvent=modelMapper.map(userEntity1,UserCreatedEvent.class);
       kafkaTemplate.send(KAFKA_CREATED_USER_TOPIC,userCreatedEvent.getId(),userCreatedEvent);
    }

}
