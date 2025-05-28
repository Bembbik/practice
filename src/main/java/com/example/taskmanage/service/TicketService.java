package com.example.taskmanage.service;

import com.example.taskmanage.dto.*;
import com.example.taskmanage.entity.*;
import com.example.taskmanage.enums.TicketPriority;
import com.example.taskmanage.enums.TicketStatus;
import com.example.taskmanage.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final CommentRepository commentRepository;
    private final DepartmentRepository departmentRepository;

    public TicketService(
            TicketRepository ticketRepository,
            UserRepository userRepository,
            TeamRepository teamRepository,
            CommentRepository commentRepository,
            DepartmentRepository departmentRepository) {
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
        this.teamRepository = teamRepository;
        this.commentRepository = commentRepository;
        this.departmentRepository = departmentRepository;
    }

    // ==== Базовые операции ====
    public Page<Ticket> getAllTickets(Pageable pageable) {
        return ticketRepository.findAll(pageable);
    }

    public Ticket createTicket(Ticket ticket) {
        if (ticket.getStatus() == null) {
            ticket.setStatus(TicketStatus.OPEN);
        }

        if (ticket.getPriority() == null) {
            ticket.setPriority(TicketPriority.MEDIUM);
        }

        ticket.setCreatedAt(LocalDateTime.now());
        ticket.setUpdatedAt(LocalDateTime.now());

        return ticketRepository.save(ticket);
    }

    public Optional<Ticket> getTicketById(Long id) {
        return ticketRepository.findById(id);
    }
    public List<Ticket> getTicketsByStatusInDepartment(Long departmentId, String status) {
        try {
            TicketStatus ticketStatus = TicketStatus.valueOf(status.toUpperCase());

            return ticketRepository.findByDepartment_IdAndStatus(departmentId, ticketStatus);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Неверный статус");
        }
    }

    public Ticket updateTicket(Long id, Ticket updated) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        if (updated.getTitle() != null) ticket.setTitle(updated.getTitle());
        if (updated.getDescription() != null) ticket.setDescription(updated.getDescription());
        if (updated.getDeadline() != null) ticket.setDeadline(updated.getDeadline());
        if (updated.getStatus() != null) ticket.setStatus(updated.getStatus());

        if (updated.getDepartment() != null) {
            Department department = departmentRepository.findById(updated.getDepartment().getId())
                    .orElseThrow(() -> new RuntimeException("Department not found"));
            ticket.setDepartment(department);
        }

        if (updated.getAssignedTeam() != null) {
            Team team = teamRepository.findById(updated.getAssignedTeam().getId())
                    .orElseThrow(() -> new RuntimeException("Team not found"));
            ticket.setAssignedTeam(team);
        }

        ticket.setUpdatedAt(LocalDateTime.now());
        return ticketRepository.save(ticket);
    }

    public void deleteTicket(Long id) {
        if (!ticketRepository.existsById(id)) {
            throw new RuntimeException("Тикет не найден");
        }
        ticketRepository.deleteById(id); // ✅ Физическое удаление
    }

    // ==== Дополнительная логика ====
    public Ticket assignTicket(Long ticketId, Long teamId, Long teamleadId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Team not found"));
        User teamlead = userRepository.findById(teamleadId)
                .orElseThrow(() -> new RuntimeException("Teamlead not found"));
        ticket.setAssignedTeam(team);
        ticket.setAssignedTeamlead(teamlead);
        ticket.setUpdatedAt(LocalDateTime.now());
        return ticketRepository.save(ticket);
    }

    public Ticket completeTicket(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
        ticket.setStatus(TicketStatus.COMPLETED);
        ticket.setUpdatedAt(LocalDateTime.now());
        return ticketRepository.save(ticket);
    }

    // В сервисе TicketService.java
    public Ticket redoTicket(Long departmentId, Long ticketId, String commentText) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Тикет не найден"));

        // Проверяем, что тикет относится к указанному отделу
        if (!ticket.getDepartment().getId().equals(departmentId)) {
            throw new RuntimeException("Тикет не относится к вашему отделу");
        }

        // Переводим статус в OPEN
        ticket.setStatus(TicketStatus.OPEN);
        ticket.setUpdatedAt(LocalDateTime.now());

        // Сохраняем комментарий
        Comment comment = new Comment();
        comment.setContent(commentText); // или setText(...) — зависит от сущности
        comment.setCreatedAt(LocalDateTime.now());
        comment.setTicket(ticket);
        comment.setUser(getCurrentUser());

        commentRepository.save(comment);

        // Сохраняем обновление тикета
        return ticketRepository.save(ticket);
    }

    public Ticket rateTicket(Long ticketId, int score) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
        ticket.setUpdatedAt(LocalDateTime.now());
        return ticketRepository.save(ticket);
    }

    public Ticket changeStatus(Long ticketId, String statusStr) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
        TicketStatus status;
        try {
            status = TicketStatus.valueOf(statusStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid status: " + statusStr);
        }
        ticket.setStatus(status);
        ticket.setUpdatedAt(LocalDateTime.now());
        return ticketRepository.save(ticket);
    }

    public Ticket comment(Long ticketId, String commentText) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        Comment comment = new Comment();
        comment.setTicket(ticket);
        comment.setContent(commentText); // или как у тебя названо поле(commentText);
        comment.setUser(getCurrentUser());
        comment.setCreatedAt(LocalDateTime.now());
        commentRepository.save(comment);

        return ticket;
    }

    public List<Ticket> getTicketsForTeam() {
        User currentUser = getCurrentUser();
        if (currentUser.getTeam() == null) {
            throw new RuntimeException("User is not assigned to any team");
        }
        return ticketRepository.findByAssignedTeam(currentUser.getTeam());
    }

    // ==== Статистика для админа ====
    public Map<TicketStatus, Long> getTicketCountsByStatus() {
        Map<TicketStatus, Long> counts = new EnumMap<>(TicketStatus.class);
        for (TicketStatus status : TicketStatus.values()) {
            counts.put(status, ticketRepository.countByStatus(status));
        }
        return counts;
    }

    public AdminDashboardStats getAdminDashboardStats() {
        long totalTicketsLong = ticketRepository.count();
        Map<TicketStatus, Long> statusCounts = getTicketCountsByStatus();
        long activeUsersLong = userRepository.count();

        int totalTickets = Math.toIntExact(totalTicketsLong);
        int activeUsers = Math.toIntExact(activeUsersLong);

        return new AdminDashboardStats(totalTickets, statusCounts, activeUsers);
    }


    // ==== Новый метод: получить всю иерархию ====
    public List<DepartmentStatsDTO> getDepartmentsWithTeamsAndTickets() {
        List<Department> departments = departmentRepository.findAll();

        return departments.stream()
                .map(department -> {
                    List<Team> teamsInDept = teamRepository.findByDepartment(department);

                    List<TeamStatsDTO> teamStatsDTOS = teamsInDept.stream()
                            .map(team -> {
                                List<Ticket> tickets = ticketRepository.findByAssignedTeam(team);

                                List<TicketStatsDTO> ticketStatsDTOS = tickets.stream()
                                        .map(t -> new TicketStatsDTO(
                                                t.getId(),
                                                t.getTitle(),
                                                t.getDescription(),
                                                t.getDeadline(),
                                                t.getPriority().toString(),
                                                t.getStatus().toString()
                                        ))
                                        .collect(Collectors.toList());

                                return new TeamStatsDTO(team.getName(), team.getId(), ticketStatsDTOS);
                            })
                            .collect(Collectors.toList());

                    return new DepartmentStatsDTO(department.getName(), department.getId(), teamStatsDTOS);
                })
                .collect(Collectors.toList());
    }

    // ==== Вспомогательные методы ====
    private User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByLogin(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
    }
    // ==== Создание тикета ====
    public Ticket createTicketFromDTO(TicketDTO dto) {
        Ticket ticket = new Ticket();
        ticket.setTitle(dto.getTitle());
        ticket.setDescription(dto.getDescription());
        ticket.setDeadline(dto.getDeadline());

        if (dto.getStatus() != null && !dto.getStatus().isEmpty()) {
            try {
                ticket.setStatus(TicketStatus.valueOf(dto.getStatus().toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Неверный статус: " + dto.getStatus());
            }
        } else {
            ticket.setStatus(TicketStatus.OPEN);
        }

        if (dto.getPriority() != null && !dto.getPriority().isEmpty()) {
            try {
                ticket.setPriority(TicketPriority.valueOf(dto.getPriority().toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Неверный приоритет: " + dto.getPriority());
            }
        } else {
            ticket.setPriority(TicketPriority.MEDIUM);
        }

        // Установка отдела
        if (dto.getDepartmentId() != null) {
            Department department = departmentRepository.findById(dto.getDepartmentId())
                    .orElseThrow(() -> new RuntimeException("Отдел не найден"));
            ticket.setDepartment(department);
        }

        // Установка команды
        if (dto.getTeamId() != null) {
            Team team = teamRepository.findById(dto.getTeamId())
                    .orElseThrow(() -> new RuntimeException("Команда не найдена"));
            ticket.setAssignedTeam(team);
        }

        ticket.setCreatedAt(LocalDateTime.now());
        ticket.setUpdatedAt(LocalDateTime.now());

        return ticketRepository.save(ticket);
    }

    // ==== Обновление тикета ====
    public Ticket updateTicketFromTicketDTO(Long ticketId, TicketDTO dto) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Тикет не найден"));

        // ==== Поля из DTO ====
        if (dto.getTitle() != null) {
            ticket.setTitle(dto.getTitle());
        }

        if (dto.getDescription() != null) {
            ticket.setDescription(dto.getDescription());
        }

        if (dto.getDeadline() != null) {
            ticket.setDeadline(dto.getDeadline());
        }

        if (dto.getStatus() != null && !dto.getStatus().isEmpty()) {
            try {
                ticket.setStatus(TicketStatus.valueOf(dto.getStatus().toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Неверный статус");
            }
        }

        if (dto.getPriority() != null && !dto.getPriority().isEmpty()) {
            try {
                ticket.setPriority(TicketPriority.valueOf(dto.getPriority().toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Неверный приоритет");
            }
        }

        // ==== Назначение команды ====
        if (dto.getTeamId() != null) {
            Team team = teamRepository.findById(dto.getTeamId())
                    .orElseThrow(() -> new RuntimeException("Команда не найдена"));

            ticket.setAssignedTeam(team);

            // Можно проверить принадлежность команды к отделу, если нужно
        }

        // ==== Назначение отдела ====
        if (dto.getDepartmentId() != null) {
            Department department = departmentRepository.findById(dto.getDepartmentId())
                    .orElseThrow(() -> new RuntimeException("Отдел не найден"));

            ticket.setDepartment(department);
        }

        // ==== Обновление времени ====
        ticket.setUpdatedAt(LocalDateTime.now());

        return ticketRepository.save(ticket);
    }

    // === Новые методы для роли DEPARTMENT ===
    // Получить команды в отделе текущего пользователя
    public List<TeamDTO> getTeamsInDepartment(Long departmentId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Отдел не найден"));

        return department.getTeams().stream()
                .map(team -> {
                    String teamleadName = team.getTeamlead() != null
                            ? team.getTeamlead().getFullName()
                            : null;

                    return new TeamDTO(
                            team.getId(),
                            team.getName(),
                            department.getId(),
                            teamleadName
                    );
                })
                .collect(Collectors.toList());
    }


    // Получить все тикеты в командах отдела текущего пользователя
    public List<TicketStatsDTO> getTicketsInDepartmentTeams(Long departmentId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Отдел не найден"));

        List<Team> teams = department.getTeams();

        return teams.stream()
                .flatMap(team -> ticketRepository.findByAssignedTeam(team).stream())
                .map(t -> new TicketStatsDTO(
                        t.getId(),
                        t.getTitle(),
                        t.getDescription(),
                        t.getDeadline(),
                        t.getPriority().toString(),
                        t.getStatus().toString()
                ))
                .collect(Collectors.toList());
    }

    // Получить тикеты по статусу в отделе текущего пользователя
    public List<Ticket> getTicketsByStatusInDepartment(String status) {
        User currentUser = getCurrentUser();
        if (currentUser.getDepartment() == null) {
            throw new IllegalStateException("User is not assigned to any department");
        }
        Department department = currentUser.getDepartment();
        List<Team> teams = teamRepository.findByDepartment(department);
        List<Ticket> tickets = teams.stream()
                .flatMap(team -> ticketRepository.findByAssignedTeam(team).stream())
                .collect(Collectors.toList());

        if (status != null && !status.isEmpty()) {
            TicketStatus ticketStatus;
            try {
                ticketStatus = TicketStatus.valueOf(status.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Invalid status: " + status);
            }
            return tickets.stream()
                    .filter(t -> t.getStatus() == ticketStatus)
                    .collect(Collectors.toList());
        }
        return tickets;
    }

    // Проверка, принадлежит ли тикет отделу текущего пользователя
    public boolean isTicketInUserDepartment(Long ticketId) {
        User currentUser = getCurrentUser();
        if (currentUser.getDepartment() == null) {
            throw new IllegalStateException("User is not assigned to any department");
        }
        Department userDepartment = currentUser.getDepartment();

        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
        Team assignedTeam = ticket.getAssignedTeam();
        if (assignedTeam == null) {
            return false;
        }
        return assignedTeam.getDepartment() != null && assignedTeam.getDepartment().getId().equals(userDepartment.getId());
    }
    public boolean isTicketInDepartment(Long ticketId, Long departmentId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Тикет не найден"));

        return ticket.getDepartment() != null &&
                ticket.getDepartment().getId().equals(departmentId);
    }
    // === Вспомогательные методы ===
    // private User getCurrentUser() {
      //  String username = SecurityContextHolder.getContext().getAuthentication().getName();
        //return userRepository.findByLogin(username)
          //      .orElseThrow(() -> new RuntimeException("User not found: " + username));
    //}
    @Transactional
    public Ticket assignTicketToTeamAndTeamlead(
            Long departmentId,
            Long ticketId,
            Long teamId,
            Long teamleadId) {

        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Отдел не найден"));

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Команда не найдена"));

        User teamlead = userRepository.findById(teamleadId)
                .orElseThrow(() -> new RuntimeException("Тимлид не найден"));

        if (!team.getDepartment().getId().equals(department.getId())) {
            throw new RuntimeException("Команда не относится к указанному отделу");
        }

        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Тикет не найден"));

        if (!ticket.getDepartment().getId().equals(department.getId())) {
            throw new RuntimeException("Тикет не относится к указанному отделу");
        }

        ticket.setAssignedTeam(team);
        ticket.setAssignedTeamlead(teamlead);
        ticket.setStatus(TicketStatus.OPEN);

        return ticketRepository.save(ticket);
    }

    // ==== Назначение тикета команде и тимлиду ====




    // ==== Отправить тикет на доработку ====


    public DepartmentWithTeamsAndTicketsDTO getTeamsWithTickets(Long departmentId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Отдел не найден"));

        List<Team> teams = department.getTeams();

        List<TeamWithTicketsDTO> teamDTOS = teams.stream()
                .map(team -> {
                    List<Ticket> tickets = ticketRepository.findByAssignedTeam(team);
                    List<TicketStatsDTO> ticketStats = tickets.stream()
                            .map(t -> new TicketStatsDTO(
                                    t.getId(),
                                    t.getTitle(),
                                    t.getDescription(),
                                    t.getDeadline(),
                                    t.getPriority().toString(),
                                    t.getStatus().toString()))
                            .collect(Collectors.toList());

                    return new TeamWithTicketsDTO(team.getId(), team.getName(), ticketStats);
                })
                .collect(Collectors.toList());

        return new DepartmentWithTeamsAndTicketsDTO(department.getId(), department.getName(), teamDTOS);
    }

    public DepartmentStatsDTO getDepartmentWithTeamsAndTickets(Long departmentId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Отдел не найден"));

        List<Team> teamList = department.getTeams();

        List<TeamStatsDTO> teamStatsDTOS = teamList.stream()
                .map(team -> {
                    List<Ticket> tickets = ticketRepository.findByAssignedTeam(team);
                    List<TicketStatsDTO> ticketStatsDTOS = tickets.stream()
                            .map(t -> new TicketStatsDTO(
                                    t.getId(),
                                    t.getTitle(),
                                    t.getDescription(),
                                    t.getDeadline(),
                                    t.getPriority().toString(),
                                    t.getStatus().toString()))
                            .collect(Collectors.toList()); // ✅ Работает на Java 8+

                    return new TeamStatsDTO(team.getName(), team.getId(), ticketStatsDTOS);
                })
                .collect(Collectors.toList());

        return new DepartmentStatsDTO(department.getName(), department.getId(), teamStatsDTOS);
    }
    public DepartmentSimpleStatsDTO getSimpleDepartmentStats(Long departmentId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Отдел не найден"));

        List<Team> teams = department.getTeams();
        int teamCount = teams.size();

        List<Ticket> allTickets = teams.stream()
                .flatMap(team -> ticketRepository.findByAssignedTeam(team).stream())
                .collect(Collectors.toList());

        int totalTickets = allTickets.size();

        int highPriorityTickets = (int) allTickets.stream()
                .filter(ticket -> TicketPriority.HIGH.toString().equals(ticket.getPriority().toString()))
                .count();

        int completedTickets = (int) allTickets.stream()
                .filter(ticket -> TicketStatus.COMPLETED.toString().equals(ticket.getStatus().toString()))
                .count();

        return new DepartmentSimpleStatsDTO(teamCount, totalTickets, highPriorityTickets, completedTickets);
    }
    public Ticket createTicketFromDTO(TicketDTO dto, Department department) {
        Ticket ticket = new Ticket();
        ticket.setTitle(dto.getTitle());
        ticket.setDescription(dto.getDescription());
        ticket.setDeadline(dto.getDeadline());
        ticket.setStatus(TicketStatus.OPEN);
        ticket.setPriority(TicketPriority.MEDIUM);

        if (dto.getTeamId() != null) {
            Team team = teamRepository.findById(dto.getTeamId())
                    .orElseThrow(() -> new RuntimeException("Команда не найдена"));
            ticket.setAssignedTeam(team);
        }

        ticket.setDepartment(department);
        ticket.setCreatedAt(LocalDateTime.now());
        ticket.setUpdatedAt(LocalDateTime.now());

        return ticketRepository.save(ticket);
    }
    public Ticket completeTicket(Long departmentId, Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Тикет не найден"));

        if (!ticket.getDepartment().getId().equals(departmentId)) {
            throw new RuntimeException("Тикет не относится к указанному отделу");
        }

        // ❌ Если IN_PROGRESS отсутствует, то просто завершаем как COMPLETED
        ticket.setStatus(TicketStatus.COMPLETED);
        ticket.setUpdatedAt(LocalDateTime.now());

        return ticketRepository.save(ticket);
    }


}