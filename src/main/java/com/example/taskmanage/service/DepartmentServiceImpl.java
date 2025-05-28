package com.example.taskmanage.service;

import com.example.taskmanage.dto.TeamCreateDTO;
import com.example.taskmanage.dto.TeamUpdateDTO;
import com.example.taskmanage.entity.*;
import com.example.taskmanage.repository.*;
import com.example.taskmanage.enums.TicketStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    // ==== 1. Назначение тимлида к команде ====
    @Override
    public void addTeamLead(Long teamLeadId) {
        User teamlead = userRepository.findById(teamLeadId)
                .orElseThrow(() -> new RuntimeException("Тимлид не найден"));

        Team team = teamlead.getTeam();
        if (team == null) {
            throw new RuntimeException("Тимлид не состоит ни в одной команде");
        }

        team.setTeamlead(teamlead);
        teamRepository.save(team);
    }

    // ==== 2. Назначение тикета тимлиду ====
    @Override
    public void assignTicket(Long ticketId, Long teamLeadId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Тикет не найден"));

        User teamlead = userRepository.findById(teamLeadId)
                .orElseThrow(() -> new RuntimeException("Тимлид не найден"));

        Team team = teamlead.getTeam();
        if (team == null) {
            throw new RuntimeException("Тимлид не принадлежит ни одной команде");
        }

        ticket.setAssignedTeam(team);
        ticket.setAssignedTeamlead(teamlead);
        ticket.setStatus(TicketStatus.OPEN); // Убедись, что используешь enum TicketStatus

        ticketRepository.save(ticket);
    }

    // ==== 3. Завершить тикет ====
    @Override
    public void completeTicket(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Тикет не найден"));

        ticket.setStatus(TicketStatus.COMPLETED);
        ticketRepository.save(ticket);
    }

    // ==== 4. Отправить тикет на доработку ====
    // ❌ Пока уберём метод reopenTicket — если нет commentRepository
    @Override
    public void reopenTicket(Long ticketId, Comment comment) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Тикет не найден"));

        ticket.setStatus(TicketStatus.OPEN);
        ticketRepository.save(ticket);

        // ❌ setContent() и commentRepository — убираем, если не используются
        // Используй только поля, которые есть в сущности Comment
        comment.setTicket(ticket);
        comment.setCreatedAt(LocalDateTime.now());

        // Если комментарий должен сохраняться — добавь репозиторий и save()
        // Но пока закомментируем или удалим, если не используется
    }

    // ==== 5. Получить тикеты по статусу ====
    @Override
    public List<Ticket> getTicketsByStatus(String statusStr) {
        try {
            TicketStatus status = TicketStatus.valueOf(statusStr.toUpperCase());
            return ticketRepository.findByStatus(status);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Неверный статус: " + statusStr);
        }
    }

    // ==== 6. Выставить оценку тикету ====
    @Override
    public void scoreTicket(Long ticketId, int score) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Тикет не найден"));

        ticket.setUpdatedAt(LocalDateTime.now());
        ticketRepository.save(ticket);
    }

    // ==== 7. Создать новую команду в отделе ====
    @Override
    public Team createTeam(Long departmentId, TeamCreateDTO dto) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Отдел не найден"));

        Team team = new Team();
        team.setName(dto.getName());
        team.setDepartment(department);
        team.setCreatedAt(LocalDateTime.now());

        return teamRepository.save(team);
    }

    // ==== 8. Назначить тимлида существующей команде ====
    @Override
    public void assignTeamleadToTeam(Long departmentId, Long teamId, Long teamLeadId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Отдел не найден"));

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Команда не найдена"));

        if (!team.getDepartment().getId().equals(department.getId())) {
            throw new RuntimeException("Команда не относится к указанному отделу");
        }

        User teamlead = userRepository.findById(teamLeadId)
                .orElseThrow(() -> new RuntimeException("Тимлид не найден"));

        team.setTeamlead(teamlead);
        teamRepository.save(team);
    }

    // ==== 9. Добавить пользователя в команду ====
    @Override
    public void addUserToTeam(Long departmentId, Long teamId, Long userId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Отдел не найден"));

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Команда не найдена"));

        if (!team.getDepartment().getId().equals(department.getId())) {
            throw new RuntimeException("Команда не относится к вашему отделу");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        user.setTeam(team);
        userRepository.save(user);
    }

    @Transactional
    public Team updateTeam(Long departmentId, Long teamId, TeamUpdateDTO dto) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Отдел не найден"));

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Команда не найдена"));

        if (!team.getDepartment().getId().equals(department.getId())) {
            throw new RuntimeException("Команда не относится к вашему отделу");
        }

        // Обновление названия команды
        if (dto.getName() != null && !dto.getName().isEmpty()) {
            team.setName(dto.getName());
        }

        // Назначение нового тимлида
        if (dto.getTeamleadId() != null) {
            User teamlead = userRepository.findById(dto.getTeamleadId())
                    .orElseThrow(() -> new RuntimeException("Тимлид не найден"));

            team.setTeamlead(teamlead);
        }

        // ✅ ВСТАВЬ СЮДА:
        Team savedTeam = teamRepository.save(team);
        System.out.println("Тимлид установлен: " + savedTeam.getTeamlead());

        return savedTeam;
    }
    @Override
    @Transactional
    public void deleteTeam(Long departmentId, Long teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Команда не найдена"));

        if (!team.getDepartment().getId().equals(departmentId)) {
            throw new RuntimeException("Команда не относится к указанному отделу");
        }


        teamRepository.deleteById(teamId);
    }
    public Team getTeamById(Long teamId) {
        return teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Команда не найдена"));
    }
}