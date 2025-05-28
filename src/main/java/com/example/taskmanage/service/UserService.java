package com.example.taskmanage.service;

import com.example.taskmanage.entity.Role;
import com.example.taskmanage.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User createUser(User user);
    Optional<User> getUserById(Long id);
    Optional<User> findByLogin(String login); // Новый метод
    List<User> getAllUsers();
    User updateUser(Long id, User user);
    void deleteUser(Long id);
    List<User> getUsersByRole(Role role);
}