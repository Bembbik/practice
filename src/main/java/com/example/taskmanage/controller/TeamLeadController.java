package com.example.taskmanage.controller;

import com.example.taskmanage.entity.Comment;
import com.example.taskmanage.entity.Ticket;
import com.example.taskmanage.entity.User;
import com.example.taskmanage.service.TeamLeadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teamlead")
public class TeamLeadController {

    @Autowired
    private TeamLeadService teamLeadService;

    // Изменить статус тикета (например, IN_PROGRESS → COMPLETED)
    @PreAuthorize("hasRole('TEAMLEAD')")
    @PutMapping("/tickets/{ticketId}/status")
    public ResponseEntity<Void> updateTicketStatus(
            @PathVariable Long ticketId,
            @RequestParam String status) {
        teamLeadService.updateTicketStatus(ticketId, status);
        return ResponseEntity.ok().build();
    }

    // Редактировать состав команды
    @PreAuthorize("hasRole('TEAMLEAD')")
    @PutMapping("/team")
    public ResponseEntity<Void> updateTeam(@RequestBody List<User> teamMembers) {
        teamLeadService.updateTeam(teamMembers);
        return ResponseEntity.ok().build();
    }

    // Оставлять комментарии к тикету
    @PreAuthorize("hasRole('TEAMLEAD')")
    @PostMapping("/tickets/{ticketId}/comments")
    public ResponseEntity<Void> addComment(
            @PathVariable Long ticketId,
            @RequestBody Comment comment) {
        teamLeadService.addComment(ticketId, comment);
        return ResponseEntity.ok().build();
    }
}