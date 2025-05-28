package com.example.taskmanage.dto;

import java.time.LocalDateTime;

public class TicketDTO {
    private String title;
    private String description;
    private LocalDateTime deadline;
    private String status;
    private String priority; // ❌ ВАЖНО: это String, если ты не передаёшь TicketPriority напрямую
    private Long departmentId;
    private Long teamId;

    // Геттеры и сеттеры
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getDeadline() { return deadline; }
    public void setDeadline(LocalDateTime deadline) { this.deadline = deadline; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getPriority() { return priority; } // ✅ Убедись, что этот метод есть
    public void setPriority(String priority) { this.priority = priority; }

    public Long getDepartmentId() { return departmentId; }
    public void setDepartmentId(Long departmentId) { this.departmentId = departmentId; }

    public Long getTeamId() { return teamId; }
    public void setTeamId(Long teamId) { this.teamId = teamId; }
}