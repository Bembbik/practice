package com.example.taskmanage.dto;

import java.util.List;

public class TeamWithTicketsDTO {
    private Long id;
    private String name;
    private List<TicketStatsDTO> tickets;

    public TeamWithTicketsDTO(Long id, String name, List<TicketStatsDTO> tickets) {
        this.id = id;
        this.name = name;
        this.tickets = tickets;
    }

    // Геттеры
    public Long getId() { return id; }
    public String getName() { return name; }
    public List<TicketStatsDTO> getTickets() { return tickets; }
}