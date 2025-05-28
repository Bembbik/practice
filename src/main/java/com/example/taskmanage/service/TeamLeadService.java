package com.example.taskmanage.service;

import com.example.taskmanage.entity.Comment;
import com.example.taskmanage.entity.Team;
import com.example.taskmanage.entity.Ticket;
import com.example.taskmanage.entity.User;
import com.example.taskmanage.enums.TicketStatus;
import com.example.taskmanage.repository.CommentRepository;
import com.example.taskmanage.repository.TicketRepository;
import com.example.taskmanage.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TeamLeadService {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    public TeamLeadService(TicketRepository ticketRepository,
                           UserRepository userRepository,
                           CommentRepository commentRepository) {
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    /**
     * Обновить статус тикета (TEAM_LEAD)
     */
    @Transactional
    public void updateTicketStatus(Long ticketId, String statusStr) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found with id " + ticketId));

        TicketStatus status;
        try {
            status = TicketStatus.valueOf(statusStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid status: " + statusStr);
        }

        ticket.setStatus(status);
        ticket.setUpdatedAt(LocalDateTime.now());

        ticketRepository.save(ticket);
    }

    /**
     * Обновить состав команды (TEAM_LEAD)
     * Заменяет текущих участников команды на новых.
     * В этом примере подразумевается, что все новые участники принадлежат к одной команде.
     */
    @Transactional
    public void updateTeam(List<User> teamMembers) {
        if (teamMembers == null || teamMembers.isEmpty()) {
            throw new RuntimeException("Team members list cannot be empty");
        }

        // Предполагается, что все пользователи имеют одно и то же поле team
        // Возьмём команду первого участника и установим ее всем остальным
        Team team = teamMembers.get(0).getTeam();

        if (team == null) {
            throw new RuntimeException("Team not assigned for the first user");
        }

        // Обновляем всех участников — присваиваем команду и сохраняем
        for (User user : teamMembers) {
            user.setTeam(team);
            userRepository.save(user);
        }
    }

    /**
     * Добавить комментарий к тикету (TEAM_LEAD)
     */
    @Transactional
    public void addComment(Long ticketId, Comment comment) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found with id " + ticketId));

        comment.setTicket(ticket);
        comment.setUser(getCurrentUser());
        comment.setCreatedAt(LocalDateTime.now());

        commentRepository.save(comment);
    }

    /**
     * Получить текущего аутентифицированного пользователя
     */
    private User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByLogin(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
    }
}
