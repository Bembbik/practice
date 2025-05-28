package com.example.taskmanage.dto;


import com.example.taskmanage.entity.Department;
import com.example.taskmanage.entity.Team;

import java.util.List;

public class AdminTicketStatsDTO {
    private final List<Department> departments;
    private final List<Team> teams;
    private final long openTickets;
    private final long highPriorityTickets;
    private final List<DepartmentStatsDTO> departmentStats;

    public AdminTicketStatsDTO(List<Department> departments, List<Team> teams, long openTickets,
                               long highPriorityTickets, List<DepartmentStatsDTO> departmentStats) {
        this.departments = departments;
        this.teams = teams;
        this.openTickets = openTickets;
        this.highPriorityTickets = highPriorityTickets;
        this.departmentStats = departmentStats;
    }

    // Геттеры
    public List<Department> getDepartments() { return departments; }
    public List<Team> getTeams() { return teams; }
    public long getOpenTickets() { return openTickets; }
    public long getHighPriorityTickets() { return highPriorityTickets; }
    public List<DepartmentStatsDTO> getDepartmentStats() { return departmentStats; }
}