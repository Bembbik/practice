package com.example.taskmanage.dto;

import java.util.List;

public class DepartmentWithTeamsAndTicketsDTO {
    private Long id;
    private String name;
    private List<TeamWithTicketsDTO> teams;

    public DepartmentWithTeamsAndTicketsDTO(Long id, String name, List<TeamWithTicketsDTO> teams) {
        this.id = id;
        this.name = name;
        this.teams = teams;
    }

    // Геттеры
    public Long getId() { return id; }
    public String getName() { return name; }
    public List<TeamWithTicketsDTO> getTeams() { return teams; }
}
