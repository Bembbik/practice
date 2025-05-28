package com.example.taskmanage.controller;

import com.example.taskmanage.entity.Ticket;
import com.example.taskmanage.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/team")
public class TeamController {

    @Autowired
    private TicketService ticketService;

    // Просмотр всех тикетов своей команды
    @PreAuthorize("hasRole('TEAM_MEMBER')")
    @GetMapping("/tickets")
    public ResponseEntity<List<Ticket>> getTeamTickets() {
        List<Ticket> tickets = ticketService.getTicketsForTeam();
        return ResponseEntity.ok(tickets);
    }

    // Просмотр конкретного тикета
    @PreAuthorize("hasRole('TEAM_MEMBER')")
    @GetMapping("/tickets/{id}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable Long id) {
        return ticketService.getTicketById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}