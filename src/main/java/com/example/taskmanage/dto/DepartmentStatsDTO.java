package com.example.taskmanage.dto;

import java.util.List;

public class DepartmentStatsDTO {
    private String department;
    private Long departmentId; // ✅ Новое поле
    private List<TeamStatsDTO> teams;

    public DepartmentStatsDTO(String department, Long departmentId, List<TeamStatsDTO> teams) {
        this.department = department;
        this.departmentId = departmentId;
        this.teams = teams;
    }

    // Геттеры
    public String getDepartment() { return department; }
    public Long getDepartmentId() { return departmentId; }
    public List<TeamStatsDTO> getTeams() { return teams; }
}