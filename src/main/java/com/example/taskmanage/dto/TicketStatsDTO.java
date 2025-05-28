package com.example.taskmanage.dto;

import java.time.LocalDateTime;

public class TicketStatsDTO {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime deadline;
    private String priority;
    private String status;

    public TicketStatsDTO(Long id, String title, String description, LocalDateTime deadline, String priority, String status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.priority = priority;
        this.status = status;
    }

    // Геттеры
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public LocalDateTime getDeadline() { return deadline; }
    public String getPriority() { return priority; }
    public String getStatus() { return status; }
}