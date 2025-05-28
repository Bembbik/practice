package com.example.taskmanage.dto;


public class UserDTO {
    private Long id;
    private String login;
    private String fullName;
    private String role;
    private Long departmentId;
    private Long teamId;

    // Пустой конструктор
    public UserDTO() {}

    // Конструктор со всеми полями
    public UserDTO(Long id, String login, String fullName, String role, Long departmentId, Long teamId) {
        this.id = id;
        this.login = login;
        this.fullName = fullName;
        this.role = role;
        this.departmentId = departmentId;
        this.teamId = teamId;
    }

    // Геттеры
    public Long getId() { return id; }
    public String getLogin() { return login; }
    public String getFullName() { return fullName; }
    public String getRole() { return role; }
    public Long getDepartmentId() { return departmentId; }
    public Long getTeamId() { return teamId; }

    // Сеттеры
    public void setId(Long id) { this.id = id; }
    public void setLogin(String login) { this.login = login; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setRole(String role) { this.role = role; }
    public void setDepartmentId(Long departmentId) { this.departmentId = departmentId; }
    public void setTeamId(Long teamId) { this.teamId = teamId; }
}