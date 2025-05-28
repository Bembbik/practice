package com.example.taskmanage.controller;

import com.example.taskmanage.dto.RegisterRequest;
import com.example.taskmanage.entity.Department;
import com.example.taskmanage.entity.Role;
import com.example.taskmanage.entity.Team;
import com.example.taskmanage.entity.User;
import com.example.taskmanage.repository.DepartmentRepository;
import com.example.taskmanage.repository.RoleRepository;
import com.example.taskmanage.repository.TeamRepository;
import com.example.taskmanage.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RoleRepository roleRepository;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> credentials) {
        String login = credentials.get("login");
        String password = credentials.get("password");

        try {
            // Аутентификация через Spring Security
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(login, password)
            );

            // Получение пользователя из БД
            Optional<User> userOptional = userRepository.findByLogin(login);
            if (!userOptional.isPresent()) {
                throw new RuntimeException("Пользователь не найден после успешной аутентификации");
            }
            User user = userOptional.get();

            // Формирование URL для редиректа
            String redirectUrl;
            switch (user.getRole().getName()) {
                case "ADMIN":
                    redirectUrl = "/admin";
                    break;
                case "DEPARTMENT":
                    redirectUrl = "/department";
                    break;
                case "TEAMLEAD":
                    redirectUrl = "/teamlead";
                    break;
                case "TEAM_MEMBER":
                    redirectUrl = "/team";
                    break;
                default:
                    throw new RuntimeException("Неизвестная роль: " + user.getRole().getName());
            }

            // Формируем JSON-ответ
            Map<String, Object> response = new HashMap<>();
            response.put("id", user.getId());
            response.put("login", user.getLogin());
            response.put("fullName", user.getFullName());
            response.put("role", user.getRole().getName());

            response.put("departmentId", user.getDepartment() != null ? user.getDepartment().getId() : null);
            response.put("teamId", user.getTeam() != null ? user.getTeam().getId() : null);

            response.put("redirectUrl", redirectUrl); // 🆕 Поле для редиректа

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Неверные учетные данные");
            return ResponseEntity.status(401).body(errorResponse);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        if (userRepository.existsByLogin(request.getLogin())) {
            return ResponseEntity.badRequest().body("Логин уже существует");
        }

        Optional<Role> roleOptional = roleRepository.findByName(request.getRole());
        if (!roleOptional.isPresent()) {
            return ResponseEntity.badRequest().body("Роль не найдена: " + request.getRole());
        }

        User user = new User();
        user.setLogin(request.getLogin());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setFullName(request.getFullName());
        user.setRole(roleOptional.get());

        if (request.getDepartmentId() != null) {
            Optional<Department> department = departmentRepository.findById(request.getDepartmentId());
            if (!department.isPresent()) {
                return ResponseEntity.badRequest().body("Департамент не найден: " + request.getDepartmentId());
            }
            user.setDepartment(department.get());
        }

        if (request.getTeamId() != null) {
            Optional<Team> team = teamRepository.findById(request.getTeamId());
            if (!team.isPresent()) {
                return ResponseEntity.badRequest().body("Команда не найдена: " + request.getTeamId());
            }
            user.setTeam(team.get());
        }

        userRepository.save(user);
        return ResponseEntity.ok("Пользователь успешно зарегистрирован");
    }

    @PostMapping("/create-initial-admin")
    public ResponseEntity<?> createInitialAdmin(@RequestBody RegisterRequest request) {
        Optional<Role> adminRoleOptional = roleRepository.findByName("ADMIN");
        if (!adminRoleOptional.isPresent()) {
            return ResponseEntity.badRequest().body("Роль ADMIN не найдена в базе данных");
        }
        Role adminRole = adminRoleOptional.get();

        if (userRepository.existsByRole(adminRole)) {
            return ResponseEntity.badRequest().body("Администратор уже существует");
        }

        if (userRepository.existsByLogin(request.getLogin())) {
            return ResponseEntity.badRequest().body("Логин уже существует");
        }

        User admin = new User();
        admin.setLogin(request.getLogin());
        admin.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        admin.setFullName(request.getFullName());
        admin.setRole(adminRole);

        userRepository.save(admin);
        return ResponseEntity.ok("Начальный администратор успешно создан");
    }
}