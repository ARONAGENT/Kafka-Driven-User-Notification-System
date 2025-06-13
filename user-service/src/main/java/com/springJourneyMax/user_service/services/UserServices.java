package com.springJourneyMax.user_service.services;

import com.springJourneyMax.user_service.dtos.UserDto;
import com.springJourneyMax.user_service.entities.UserEntity;
import com.springJourneyMax.user_service.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServices {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserDto createUser(UserDto userDto){
       UserEntity userEntity= modelMapper.map(userDto, UserEntity.class);
       UserEntity userEntity1=  userRepository.save(userEntity);
       return modelMapper.map(userEntity1,UserDto.class);
    }

}
