package com.springJourneyMax.user_service.controllers;

import com.springJourneyMax.user_service.dtos.UserDto;
import com.springJourneyMax.user_service.services.UserServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/createUser")
@RequiredArgsConstructor
public class UserCreatedController {

    private final UserServices userServices;

    @PostMapping("/add")
    public ResponseEntity<String> createUser(@RequestBody UserDto userDto){
        userServices.createUser(userDto);
        return ResponseEntity.ok("user is created");
    }
}
