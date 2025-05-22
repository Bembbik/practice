package com.example.taskmanager.controller;

import com.example.taskmanager.model.User;
import com.example.taskmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "redirect:/register.html";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam("name") String name,
                               @RequestParam("username") String username,
                               @RequestParam("password") String password,
                               @RequestParam("confirmPassword") String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            return "redirect:/register.html?error=passwordsDontMatch";
        }

        User user = new User();
        user.setFullName(name);
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        if (userRepository.findByUsername(username).isPresent()) {
            return "redirect:/register.html?error=usernameExists";
        }
        userRepository.save(user);
        User savedUser = userRepository.save(user);
        System.out.println("User saved: " + savedUser);

        // Авторизация пользователя вручную
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        SecurityContextHolder.getContext().setAuthentication(auth);

        return "redirect:/"; // или на страницу профиля/главную
    }
    @Controller
    public class LoginController {
        @GetMapping("/login")
        public String login() {
            return "redirect:/login.html";
        }
    }

}
