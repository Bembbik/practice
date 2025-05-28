package com.example.taskmanage.dto;

public class DepartmentSimpleStatsDTO {
    private final int teamCount;
    private final int totalTickets;
    private final int highPriorityTickets;
    private final int completedTickets;

    public DepartmentSimpleStatsDTO(int teamCount, int totalTickets, int highPriorityTickets, int completedTickets) {
        this.teamCount = teamCount;
        this.totalTickets = totalTickets;
        this.highPriorityTickets = highPriorityTickets;
        this.completedTickets = completedTickets;
    }

    // Геттеры
    public int getTeamCount() {
        return teamCount;
    }

    public int getTotalTickets() {
        return totalTickets;
    }

    public int getHighPriorityTickets() {
        return highPriorityTickets;
    }

    public int getCompletedTickets() {
        return completedTickets;
    }
}