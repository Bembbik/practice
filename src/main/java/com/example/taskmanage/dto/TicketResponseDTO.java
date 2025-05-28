package com.example.taskmanage.dto;

import com.example.taskmanage.entity.Ticket;
import java.time.LocalDateTime;

public class TicketResponseDTO {
    private final Long id;
    private final String title;
    private final String description;
    private final LocalDateTime deadline;
    private final String status;
    private final String priority;
    private final Long departmentId;
    private final String departmentName;
    private final Long teamId;
    private final String teamName;

    public TicketResponseDTO(Ticket ticket) {
        this.id = ticket.getId();
        this.title = ticket.getTitle();
        this.description = ticket.getDescription();
        this.deadline = ticket.getDeadline();

        // Убедись, что статус и приоритет не null
        this.status = ticket.getStatus() != null ? ticket.getStatus().toString() : "UNKNOWN";
        this.priority = ticket.getPriority() != null ? ticket.getPriority().toString() : "MEDIUM";

        // Безопасное получение данных отдела
        if (ticket.getDepartment() != null && ticket.getDepartment().getId() != null) {
            this.departmentId = ticket.getDepartment().getId();
            this.departmentName = ticket.getDepartment().getName();
        } else {
            this.departmentId = null;
            this.departmentName = null;
        }

        // Безопасное получение данных команды
        if (ticket.getAssignedTeam() != null && ticket.getAssignedTeam().getId() != null) {
            this.teamId = ticket.getAssignedTeam().getId();
            this.teamName = ticket.getAssignedTeam().getName();
        } else {
            this.teamId = null;
            this.teamName = null;
        }
    }

    // Геттеры — только read-only
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public LocalDateTime getDeadline() { return deadline; }
    public String getStatus() { return status; }
    public String getPriority() { return priority; }
    public Long getDepartmentId() { return departmentId; }
    public String getDepartmentName() { return departmentName; }
    public Long getTeamId() { return teamId; }
    public String getTeamName() { return teamName; }

    @Override
    public String toString() {
        return "TicketResponseDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", deadline=" + deadline +
                ", status='" + status + '\'' +
                ", priority='" + priority + '\'' +
                ", departmentId=" + departmentId +
                ", departmentName='" + departmentName + '\'' +
                ", teamId=" + teamId +
                ", teamName='" + teamName + '\'' +
                '}';
    }
}