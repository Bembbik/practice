package com.example.taskmanage.dto;

import java.util.Map;
import com.example.taskmanage.enums.TicketStatus;

public class AdminDashboardStats {
    private final int totalTickets;
    private final int pendingTickets;
    private final int resolvedTickets;
    private final int activeUsers;

    public AdminDashboardStats(int totalTickets, Map<TicketStatus, Long> statusCounts, int activeUsers) {
        this.totalTickets = totalTickets;
        this.pendingTickets = Math.toIntExact(statusCounts.getOrDefault(TicketStatus.OPEN, 0L));
        this.resolvedTickets = Math.toIntExact(statusCounts.getOrDefault(TicketStatus.COMPLETED, 0L));
        this.activeUsers = activeUsers;
    }

    // Геттеры
    public int getTotalTickets() { return totalTickets; }
    public int getPendingTickets() { return pendingTickets; }
    public int getResolvedTickets() { return resolvedTickets; }
    public int getActiveUsers() { return activeUsers; }
}