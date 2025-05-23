package com.example.taskmanager.controller;// FriendController.java

import com.example.taskmanager.dto.UserDto;
import com.example.taskmanager.service.FriendService;
import com.example.taskmanager.model.User;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/friends")
@RequiredArgsConstructor
public class FriendController {

    private final Logger logger = LoggerFactory.getLogger(FriendController.class);

    private final FriendService friendService;

    @GetMapping("/user/{userId}")
    public List<UserDto> getFriends(@PathVariable Long userId) {
        logger.info("Получение списка друзей для пользователя {}", userId);
        return friendService.getFriends(userId);
    }




    @PostMapping("/user/{userId}/add/{friendId}")
    public void addFriend(@PathVariable("userId") Long userId, @PathVariable("friendId") Long friendId) {
        logger.info("Добавление друга {} к пользователю {}", friendId, userId);
        friendService.addFriend(userId, friendId);
    }
}