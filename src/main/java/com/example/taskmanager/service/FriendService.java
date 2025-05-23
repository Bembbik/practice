// FriendService.java
package com.example.taskmanager.service;
import com.example.taskmanager.dto.UserDto;
import com.example.taskmanager.model.User;
import com.example.taskmanager.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class FriendService {
    private final UserRepository userRepository;

    @Transactional
    public void addFriend(Long userId, Long friendId) {
        if (userId.equals(friendId)) {
            throw new IllegalArgumentException("Нельзя добавить самого себя в друзья.");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        User friend = userRepository.findById(friendId)
                .orElseThrow(() -> new RuntimeException("Друг не найден"));

        user.addFriend(friend);
    }



    public List<UserDto> getFriends(Long userId) {
        return userRepository.findById(userId)
                .map(user -> user.getFriends().stream()
                        .map(friend -> new UserDto(friend.getId(), friend.getFullName(), friend.getUsername()))
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }

}
