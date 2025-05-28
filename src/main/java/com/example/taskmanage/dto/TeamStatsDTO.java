package com.example.taskmanage.dto;

import java.util.List;

import java.util.List;

public class TeamStatsDTO {
    private String name;
    private Long teamId; // ✅ Новое поле
    private List<TicketStatsDTO> tickets;

    public TeamStatsDTO(String name, Long teamId, List<TicketStatsDTO> tickets) {
        this.name = name;
        this.teamId = teamId;
        this.tickets = tickets;
    }

    // Геттеры
    public String getName() { return name; }
    public Long getTeamId() { return teamId; }
    public List<TicketStatsDTO> getTickets() { return tickets; }
}
