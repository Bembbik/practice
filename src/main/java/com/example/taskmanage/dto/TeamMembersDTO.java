package com.example.taskmanage.dto;

import java.util.List;

public class TeamMembersDTO {
    private final Long teamId;
    private final String teamName;
    private final UserDTO teamlead;
    private final List<UserDTO> members;

    public TeamMembersDTO(Long teamId, String teamName, UserDTO teamlead, List<UserDTO> members) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.teamlead = teamlead;
        this.members = members;
    }

    // Геттеры
    public Long getTeamId() { return teamId; }
    public String getTeamName() { return teamName; }
    public UserDTO getTeamlead() { return teamlead; }
    public List<UserDTO> getMembers() { return members; }
}