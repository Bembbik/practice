package com.example.taskmanage.controller;

import com.example.taskmanage.entity.Ticket;
import com.example.taskmanage.enums.TicketStatus;
import com.example.taskmanage.service.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tickets")

public class TicketController {
    @Autowired
    private  TicketService ticketService;

    @GetMapping("/admin/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Page<Ticket>> getAllTickets(Pageable pageable) {
        return ResponseEntity.ok(ticketService.getAllTickets(pageable));
    }

    @PostMapping("/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Ticket> createTicket(@Valid @RequestBody Ticket ticket) {
        return ResponseEntity.ok(ticketService.createTicket(ticket));
    }

    @GetMapping("/admin/counts-by-status")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Map<TicketStatus, Long>> getTicketCountsByStatus() {
        return ResponseEntity.ok(ticketService.getTicketCountsByStatus());
    }

    @PostMapping("/department/assign/{ticketId}")
    @PreAuthorize("hasRole('ROLE_DEPARTMENT')")
    public ResponseEntity<Ticket> assignTicket(
            @PathVariable Long ticketId,
            @RequestParam Long teamId,
            @RequestParam Long teamleadId
    ) {
        if (!ticketService.isTicketInUserDepartment(ticketId)) {
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.ok(ticketService.assignTicket(ticketId, teamId, teamleadId));
    }

    @PostMapping("/department/complete/{ticketId}")
    @PreAuthorize("hasRole('ROLE_DEPARTMENT')")
    public ResponseEntity<Ticket> completeTicket(@PathVariable Long ticketId) {
        if (!ticketService.isTicketInUserDepartment(ticketId)) {
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.ok(ticketService.completeTicket(ticketId));
    }

    @PostMapping("/department/{departmentId}/redo/{ticketId}")
    @PreAuthorize("hasRole('ROLE_DEPARTMENT')")
    public ResponseEntity<Ticket> redoTicket(
            @PathVariable Long departmentId,
            @PathVariable Long ticketId,
            @RequestParam String comment) {
        if (!ticketService.isTicketInDepartment(departmentId, ticketId)) {
            return ResponseEntity.status(403).build();
        }
        Ticket updated = ticketService.redoTicket(departmentId, ticketId, comment);
        return ResponseEntity.ok(updated);
    }

    @PostMapping("/department/rate/{ticketId}")
    @PreAuthorize("hasRole('ROLE_DEPARTMENT')")
    public ResponseEntity<Ticket> rateTicket(@PathVariable Long ticketId, @RequestParam int score) {
        if (!ticketService.isTicketInUserDepartment(ticketId)) {
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.ok(ticketService.rateTicket(ticketId, score));
    }

    @PostMapping("/teamlead/status/{ticketId}")
    @PreAuthorize("hasRole('ROLE_TEAMLEAD')")
    public ResponseEntity<Ticket> changeStatus(
            @PathVariable Long ticketId,
            @RequestParam String status
    ) {
        return ResponseEntity.ok(ticketService.changeStatus(ticketId, status));
    }

    @PostMapping("/teamlead/comment/{ticketId}")
    @PreAuthorize("hasRole('ROLE_TEAMLEAD')")
    public ResponseEntity<Ticket> commentAsTeamlead(@PathVariable Long ticketId, @RequestParam String comment) {
        return ResponseEntity.ok(ticketService.comment(ticketId, comment));
    }

    @GetMapping("/team/my")
    @PreAuthorize("hasRole('ROLE_TEAM')")
    public ResponseEntity<List<Ticket>> getMyTickets() {
        return ResponseEntity.ok(ticketService.getTicketsForTeam());
    }
}