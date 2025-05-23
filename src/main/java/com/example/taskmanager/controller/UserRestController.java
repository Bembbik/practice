// src/main/java/com/example/taskmanager/controller/UserRestController.java

package com.example.taskmanager.controller;

import com.example.taskmanager.model.User;
import com.example.taskmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    @Autowired
    private UserRepository userRepository;

    // GET /api/users/me → возвращает данные текущего пользователя
    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUser(HttpServletRequest request) {
        String username = request.getUserPrincipal().getName();

        return userRepository.findByUsername(username)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
    }
}