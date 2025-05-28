package com.example.taskmanage.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reminders")
public class Reminder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    @Column(name = "reminder_type", nullable = false, length = 30)
    private String reminderType;

    @ManyToOne
    @JoinColumn(name = "trigger_ticket_id")
    private Ticket triggerTicket;

    private LocalDateTime scheduledTime;

    @Column(name = "interval_minutes")
    private Integer intervalMinutes;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    // Getters and setters
}

