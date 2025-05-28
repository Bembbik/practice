package com.example.taskmanage.service;

import com.example.taskmanage.dto.TeamCreateDTO;
import com.example.taskmanage.entity.Comment;
import com.example.taskmanage.entity.Team;
import com.example.taskmanage.entity.User;
import com.example.taskmanage.entity.Ticket;

import java.util.List;

public interface DepartmentService {

    void addTeamLead(Long teamLeadId);
    void assignTicket(Long ticketId, Long teamLeadId);
    void completeTicket(Long ticketId);
    void reopenTicket(Long ticketId, Comment comment);
    List<Ticket> getTicketsByStatus(String status);
    void scoreTicket(Long ticketId, int score);
    void deleteTeam(Long departmentId, Long teamId);
    Team createTeam(Long departmentId, TeamCreateDTO dto);
    void assignTeamleadToTeam(Long departmentId, Long teamId, Long teamLeadId);
    void addUserToTeam(Long departmentId, Long teamId, Long userId);
}