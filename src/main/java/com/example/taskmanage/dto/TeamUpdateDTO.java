package com.example.taskmanage.dto;
public class TeamUpdateDTO {
    private String name;
    private Long teamleadId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getTeamleadId() {
        return teamleadId;
    }

    public void setTeamleadId(Long teamleadId) {
        this.teamleadId = teamleadId;
    }
}