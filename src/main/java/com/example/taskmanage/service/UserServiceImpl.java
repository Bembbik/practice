package com.example.taskmanage.service;

import com.example.taskmanage.entity.Role;
import com.example.taskmanage.entity.User;
import com.example.taskmanage.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User createUser(User user) {
        // Кодируем пароль, если он не закодирован
        if (user.getPasswordHash() != null && !user.getPasswordHash().startsWith("$2a$")) {
            user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        }
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(Long id, User user) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Пользователь с ID " + id + " не найден"));

        if (user.getLogin() != null && !user.getLogin().isEmpty()) {
            existingUser.setLogin(user.getLogin());
        }
        if (user.getFullName() != null) {
            existingUser.setFullName(user.getFullName());
        }
        if (user.getPasswordHash() != null && !user.getPasswordHash().isEmpty()) {
            // Кодируем пароль, если он не закодирован
            if (!user.getPasswordHash().startsWith("$2a$")) {
                existingUser.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
            } else {
                existingUser.setPasswordHash(user.getPasswordHash());
            }
        }
        if (user.getRole() != null) {
            existingUser.setRole(user.getRole());
        }
        if (user.getDepartment() != null) {
            existingUser.setDepartment(user.getDepartment());
        }
        if (user.getTeam() != null) {
            existingUser.setTeam(user.getTeam());
        }

        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("Пользователь с ID " + id + " не найден");
        }
        userRepository.deleteById(id);
    }

    @Override
    public List<User> getUsersByRole(Role role) {
        return userRepository.findByRole(role);
    }
}

// Кастомное исключение
class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}