package com.example.taskmanage.controller;

import com.example.taskmanage.dto.*;
import com.example.taskmanage.entity.Department;
import com.example.taskmanage.entity.Role;
import com.example.taskmanage.entity.Team;
import com.example.taskmanage.entity.Ticket;
import com.example.taskmanage.entity.User;
import com.example.taskmanage.repository.DepartmentRepository;
import com.example.taskmanage.repository.RoleRepository;
import com.example.taskmanage.repository.TeamRepository;
import com.example.taskmanage.service.TicketService;
import com.example.taskmanage.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final TeamRepository teamRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final TicketService ticketService;
    private final RoleRepository roleRepository;
    private final DepartmentRepository departmentRepository;

    public AdminController(
            TeamRepository teamRepository,
            PasswordEncoder passwordEncoder,
            UserService userService,
            TicketService ticketService,
            RoleRepository roleRepository,
            DepartmentRepository departmentRepository) {
        this.teamRepository = teamRepository;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.ticketService = ticketService;
        this.roleRepository = roleRepository;
        this.departmentRepository = departmentRepository;
    }
    // ==== Иерархия отдел команда тикеты ====
    @GetMapping("/admin-ticket")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<DepartmentStatsDTO>> getHierarchy() {
        List<DepartmentStatsDTO> hierarchy = ticketService.getDepartmentsWithTeamsAndTickets();
        return ResponseEntity.ok(hierarchy);
    }
    // ==== Пользователи ====


    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        List<UserDTO> userDTOs = users.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList()); // ✅ Работает везде
        return ResponseEntity.ok(userDTOs);
    }

    private UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setLogin(user.getLogin());
        dto.setFullName(user.getFullName());
        if (user.getRole() != null) {
            dto.setRole(user.getRole().getName()); // или просто String role;
        }
        if (user.getDepartment() != null) {
            dto.setDepartmentId(user.getDepartment().getId());
        }
        if (user.getTeam() != null) {
            dto.setTeamId(user.getTeam().getId());
        }
        return dto;
    }
    @PostMapping("/users/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> createUser(@RequestBody UserCreateDTO dto) {

        Role role = roleRepository.findByName(dto.getRole())
                .orElseThrow(() -> new RuntimeException("Роль не найдена"));

        Department department = null;

        if ("DEPARTMENT".equals(dto.getRole())) {
            if (dto.getDepartmentId() != null) {
                department = departmentRepository.findById(dto.getDepartmentId())
                        .orElseThrow(() -> new RuntimeException("Отдел не найден"));
            } else if (dto.getNewDepartmentName() != null && !dto.getNewDepartmentName().trim().isEmpty()) {
                department = new Department();
                department.setName(dto.getNewDepartmentName());
                department.setCreatedAt(LocalDateTime.now());
                department = departmentRepository.save(department);
            } else {
                throw new RuntimeException("Укажите ID существующего отдела или имя нового");
            }
        }

        User user = new User();
        user.setLogin(dto.getLogin());
        user.setFullName(dto.getFullName());
        user.setRole(role);
        user.setPasswordHash(passwordEncoder.encode(dto.getPassword()));

        if (department != null) {
            user.setDepartment(department);
        }

        User createdUser = userService.createUser(user);

        return ResponseEntity.ok(createdUser);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/users/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        User existingUser = userService.getUserById(id)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        existingUser.setFullName(userDTO.getFullName());

        Role role = roleRepository.findByName(userDTO.getRole())
                .orElseThrow(() -> new RuntimeException("Роль не найдена"));
        existingUser.setRole(role);

        if (userDTO.getDepartmentId() != null) {
            Department department = departmentRepository.findById(userDTO.getDepartmentId())
                    .orElseThrow(() -> new RuntimeException("Отдел не найден"));
            existingUser.setDepartment(department);
        }

        if (userDTO.getTeamId() != null) {
            Team team = teamRepository.findById(userDTO.getTeamId())
                    .orElseThrow(() -> new RuntimeException("Команда не найдена"));
            existingUser.setTeam(team);
        }

        User updated = userService.updateUser(id, existingUser);

        // ❗ Не забудь конвертировать в DTO
        return ResponseEntity.ok(convertToDTO(updated));
    }
    @DeleteMapping("/tickets/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long id) {
        ticketService.deleteTicket(id);
        return ResponseEntity.noContent().build();
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // ==== Тикеты ====

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/tickets")
    public ResponseEntity<Page<Ticket>> getAllTickets(Pageable pageable) {
        Page<Ticket> tickets = ticketService.getAllTickets(pageable);
        return ResponseEntity.ok(tickets);
    }

    @PutMapping("/tickets/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Ticket> updateTicket(@PathVariable Long id, @RequestBody TicketDTO dto) {
        return ResponseEntity.ok(ticketService.updateTicketFromTicketDTO(id, dto));
    }

    // ==== Статистика ====

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/stats")
    public ResponseEntity<AdminDashboardStats> getAdminStats() {
        AdminDashboardStats stats = ticketService.getAdminDashboardStats();
        return ResponseEntity.ok(stats);
    }
    @PostMapping("/tickets")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Ticket> createTicket(@Valid @RequestBody TicketDTO ticketDTO) {
        Ticket created = ticketService.createTicketFromDTO(ticketDTO);
        return ResponseEntity.ok(created);
    }
    // ==== Список отделов ====
    @GetMapping("/departments")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<DepartmentDTO>> getAllDepartments() {
        List<Department> departments = departmentRepository.findAll();
        List<DepartmentDTO> departmentDTOs = departments.stream()
                .map(dept -> new DepartmentDTO(dept.getId(), dept.getName()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(departmentDTOs);
    }
}