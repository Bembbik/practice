package com.example.taskmanage.controller;

import com.example.taskmanage.dto.*;
import com.example.taskmanage.entity.Department;
import com.example.taskmanage.entity.Ticket;
import com.example.taskmanage.entity.Team;
import com.example.taskmanage.entity.User;
import com.example.taskmanage.enums.TicketPriority;
import com.example.taskmanage.enums.TicketStatus;
import com.example.taskmanage.repository.DepartmentRepository;
import com.example.taskmanage.repository.TeamRepository;
import com.example.taskmanage.repository.TicketRepository;
import com.example.taskmanage.repository.UserRepository;
import com.example.taskmanage.service.DepartmentService;
import com.example.taskmanage.service.DepartmentServiceImpl;
import com.example.taskmanage.service.TicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/department")
@PreAuthorize("hasRole('DEPARTMENT')") // Общая проверка роли
public class DepartmentController {
    private final DepartmentServiceImpl departmentServiceImpl;
    private final DepartmentService departmentService;
    private final TicketService ticketService;
    private final DepartmentRepository departmentRepository;
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    public DepartmentController(DepartmentService departmentService, TicketService ticketService, DepartmentRepository departmentRepository, TeamRepository teamRepository, UserRepository userRepository, DepartmentServiceImpl departmentServiceImpl) {
        this.departmentService = departmentService;
        this.ticketService = ticketService;
        this.departmentRepository = departmentRepository;
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
        this.departmentServiceImpl = departmentServiceImpl;
    }
    // ==== Получить всех участников команд ====
    @GetMapping("/team-members")
    public ResponseEntity<List<UserDTO>> getAllTeamMembers() {
        List<User> members = userRepository.findAllTeamMembers(); // или findByRole(...)

        List<UserDTO> dtos = members.stream()
                .map(u -> new UserDTO(
                        u.getId(),
                        u.getLogin(),
                        u.getFullName(),
                        u.getRole().getName(), // или "TEAM_MEMBER", если ты фильтруешь на уровне репозитория
                        u.getDepartment() != null ? u.getDepartment().getId() : null,
                        u.getTeam() != null ? u.getTeam().getId() : null))
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    // ==== Получить всех тимлидов ====
    @GetMapping("/team-leads")
    public ResponseEntity<List<UserDTO>> getAllTeamLeads() {
        List<User> leads = userRepository.findAllTeamLeads(); // или findByRole(...)

        List<UserDTO> dtos = leads.stream()
                .map(u -> new UserDTO(
                        u.getId(),
                        u.getLogin(),
                        u.getFullName(),
                        u.getRole().getName(),
                        u.getDepartment() != null ? u.getDepartment().getId() : null,
                        u.getTeam() != null ? u.getTeam().getId() : null))
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }
    // ==== Список команд в отделе ====
    @GetMapping("/{departmentId}/teams")
    public ResponseEntity<List<TeamDTO>> getTeamsByDepartmentId(@PathVariable Long departmentId) {
        List<TeamDTO> teams = ticketService.getTeamsInDepartment(departmentId);
        return ResponseEntity.ok(teams);
    }
    // ==== Список команд с тикетами (иерархия) ====
    @GetMapping("/{departmentId}/teams-with-tickets")
    public ResponseEntity<DepartmentWithTeamsAndTicketsDTO> getTeamsWithTickets(
            @PathVariable Long departmentId) {
        DepartmentWithTeamsAndTicketsDTO dto = ticketService.getTeamsWithTickets(departmentId);
        return ResponseEntity.ok(dto);
    }
    @GetMapping("/{departmentId}/stats")
    @PreAuthorize("hasRole('DEPARTMENT') and #departmentId != null")
    public ResponseEntity<DepartmentSimpleStatsDTO> getSimpleDepartmentStats(@PathVariable Long departmentId) {
        DepartmentSimpleStatsDTO stats = ticketService.getSimpleDepartmentStats(departmentId);
        return ResponseEntity.ok(stats);
    }
    // ==== Список тикетов всех команд отдела ====
    @GetMapping("/{departmentId}/tickets")
    public ResponseEntity<List<TicketStatsDTO>> getTicketsByDepartmentId(@PathVariable Long departmentId) {
        List<TicketStatsDTO> tickets = ticketService.getTicketsInDepartmentTeams(departmentId);
        return ResponseEntity.ok(tickets);
    }
    @PostMapping("/{departmentId}/tickets/create")
    public ResponseEntity<Ticket> createTicketInDepartment(
            @PathVariable Long departmentId,
            @RequestBody TicketDTO ticketDTO) {

        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Отдел не найден"));

        // Создаем новый тикет
        Ticket ticket = new Ticket();
        ticket.setTitle(ticketDTO.getTitle());
        ticket.setDescription(ticketDTO.getDescription());
        ticket.setDeadline(ticketDTO.getDeadline());

        // Статус и приоритет по умолчанию
        ticket.setStatus(TicketStatus.OPEN);
        ticket.setPriority(TicketPriority.MEDIUM);

        // Установка отдела
        ticket.setDepartment(department);

        // Установка команды (если указано)
        if (ticketDTO.getTeamId() != null) {
            Team team = teamRepository.findById(ticketDTO.getTeamId())
                    .orElseThrow(() -> new RuntimeException("Команда не найдена"));
            ticket.setAssignedTeam(team);
        }

        // Сохраняем тикет
        Ticket createdTicket = ticketService.createTicket(ticket);
        return ResponseEntity.ok(createdTicket);
    }
    // ==== Получить список тикетов по статусу ====
    @GetMapping("/{departmentId}/tickets/status")
    public ResponseEntity<List<Ticket>> getTicketsByStatus(
            @PathVariable Long departmentId,
            @RequestParam String status) {
        List<Ticket> tickets = ticketService.getTicketsByStatusInDepartment(departmentId, status);
        return ResponseEntity.ok(tickets);
    }

    // ==== Создать новую команду в отделе ====
    @PostMapping("/{departmentId}/teams/create")
    public ResponseEntity<TeamDTO> createTeamInDepartment(
            @PathVariable Long departmentId,
            @RequestBody TeamCreateDTO teamCreateDTO) {

        Team created = departmentService.createTeam(departmentId, teamCreateDTO);

        // Получаем ID отдела из созданной команды или напрямую из departmentId
        String teamleadName = null;
        if (created.getTeamlead() != null) {
            teamleadName = created.getTeamlead().getFullName();
        }

        TeamDTO response = new TeamDTO(
                created.getId(),
                created.getName(),
                departmentId,
                teamleadName
        );

        return ResponseEntity.ok(response);
    }

    // ==== Назначить тимлида существующей команде ====
    @PostMapping("/{departmentId}/teams/{teamId}/assign-teamlead")
    public ResponseEntity<Void> assignTeamleadToTeam(
            @PathVariable Long departmentId,
            @PathVariable Long teamId,
            @RequestParam Long teamLeadId) {

        departmentService.assignTeamleadToTeam(departmentId, teamId, teamLeadId);
        return ResponseEntity.ok().build();
    }

    // ==== Добавить участника в команду ====
    @PostMapping("/{departmentId}/teams/{teamId}/add-member")
    public ResponseEntity<Void> addMemberToTeam(
            @PathVariable Long departmentId,
            @PathVariable Long teamId,
            @RequestParam Long userId) {

        departmentService.addUserToTeam(departmentId, teamId, userId);
        return ResponseEntity.ok().build();
    }

    // ==== Назначение тикета команде и тимлиду ====
    @PostMapping("/{departmentId}/tickets/{ticketId}/assign")
    public ResponseEntity<Void> assignTicket(
            @PathVariable Long departmentId,
            @PathVariable Long ticketId,
            @RequestParam Long teamId,
            @RequestParam Long teamleadId) {

        ticketService.assignTicketToTeamAndTeamlead(departmentId, ticketId, teamId, teamleadId);
        return ResponseEntity.ok().build();
    }

    // ==== Завершение тикета ====
    @PostMapping("/{departmentId}/tickets/{ticketId}/complete")
    public ResponseEntity<Ticket> completeTicket(
            @PathVariable Long departmentId,
            @PathVariable Long ticketId) {

        Ticket ticket = ticketService.completeTicket(departmentId, ticketId);
        return ResponseEntity.ok(ticket);
    }

    // ==== Отправить тикет на доработку ====
    @PostMapping("/{departmentId}/tickets/{ticketId}/redo")
    public ResponseEntity<Void> redoTicket(
            @PathVariable Long departmentId,
            @PathVariable Long ticketId,
            @RequestParam String comment) {

        ticketService.redoTicket(departmentId, ticketId, comment);
        return ResponseEntity.ok().build();
    }
    // ==== Редактировать тикет (только если он принадлежит отделу)
    @PutMapping("/{departmentId}/tickets/{ticketId}")
    public ResponseEntity<Ticket> updateTicketInDepartment(
            @PathVariable Long departmentId,
            @PathVariable Long ticketId,
            @RequestBody TicketDTO dto) {

        if (!ticketService.isTicketInDepartment(ticketId, departmentId)) {
            return ResponseEntity.status(403).build(); // ❌ Нет доступа
        }

        Ticket updated = ticketService.updateTicketFromTicketDTO(ticketId, dto);
        return ResponseEntity.ok(updated);
    }

    // ==== Удалить тикет (только если он принадлежит отделу)
    @DeleteMapping("/{departmentId}/tickets/{ticketId}")
    public ResponseEntity<Void> deleteTicketInDepartment(
            @PathVariable Long departmentId,
            @PathVariable Long ticketId) {

        if (!ticketService.isTicketInDepartment(ticketId, departmentId)) {
            return ResponseEntity.status(403).build();
        }

        ticketService.deleteTicket(ticketId);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{departmentId}/teams/{teamId}")
    @PreAuthorize("hasRole('DEPARTMENT')")
    public ResponseEntity<TeamDTO> updateTeam(
            @PathVariable Long departmentId,
            @PathVariable Long teamId,
            @RequestBody TeamUpdateDTO dto) {

        Team updated = departmentServiceImpl.updateTeam(departmentId, teamId, dto);

        String teamleadName = null;
        if (updated.getTeamlead() != null) {
            teamleadName = updated.getTeamlead().getFullName();
        }

        TeamDTO response = new TeamDTO(
                updated.getId(),
                updated.getName(),
                departmentId,
                teamleadName
        );

        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/{departmentId}/teams/{teamId}")
    @PreAuthorize("hasRole('DEPARTMENT') and #departmentId != null")
    public ResponseEntity<Void> deleteTeam(
            @PathVariable Long departmentId,
            @PathVariable Long teamId) {

        departmentServiceImpl.deleteTeam(departmentId, teamId);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/teams/{teamId}/members")
    @PreAuthorize("hasRole('DEPARTMENT')")
    public ResponseEntity<TeamMembersDTO> getTeamMembers(@PathVariable Long teamId) {
        Team team = departmentServiceImpl.getTeamById(teamId);

        User teamlead = team.getTeamlead();
        List<User> users = team.getMembers();

        // Конвертируем тимлида в UserDTO
        UserDTO teamleadDTO = null;
        if (teamlead != null) {
            teamleadDTO = new UserDTO(
                    teamlead.getId(),
                    teamlead.getLogin(),
                    teamlead.getFullName(),
                    teamlead.getRole().getName(),
                    teamlead.getDepartment() != null ? teamlead.getDepartment().getId() : null,
                    teamlead.getTeam() != null ? teamlead.getTeam().getId() : null
            );
        }

        // Конвертируем список пользователей в List<UserDTO>
        List<UserDTO> memberDTOs = users.stream()
                .map(u -> new UserDTO(
                        u.getId(),
                        u.getLogin(),
                        u.getFullName(),
                        u.getRole().getName(),
                        u.getDepartment() != null ? u.getDepartment().getId() : null,
                        u.getTeam() != null ? u.getTeam().getId() : null))
                .collect(Collectors.toList());

        // Создаем TeamMembersDTO
        TeamMembersDTO dto = new TeamMembersDTO(
                team.getId(),
                team.getName(),
                teamleadDTO,
                memberDTOs
        );

        return ResponseEntity.ok(dto);
    }
}