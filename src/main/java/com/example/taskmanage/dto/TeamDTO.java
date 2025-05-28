package com.example.taskmanage.dto;

import java.util.List;

public class TeamDTO {
    private Long id;
    private String name;
    private Long departmentId;
    private String teamleadName; // Имя тимлида (или null, если тимлид не назначен)

    // Конструктор
    public TeamDTO(Long id, String name, Long departmentId, String teamleadName) {
        this.id = id;
        this.name = name;
        this.departmentId = departmentId;
        this.teamleadName = teamleadName;
    }

    // Геттеры
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public String getTeamleadName() {
        return teamleadName;
    }

    // Сеттеры
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public void setTeamleadName(String teamleadName) {
        this.teamleadName = teamleadName;
    }
}