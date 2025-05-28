package com.example.taskmanage.dto;

public class RegisterRequest {

    private String login;
    private String password;
    private String fullName;
    private String role;
    private Long departmentId;
    private Long teamId;

    // Конструктор по умолчанию
    public RegisterRequest() {
    }

    // Конструктор со всеми полями
    public RegisterRequest(String login, String password, String fullName, String role,
                           Long departmentId, Long teamId) {
        this.login = login;
        this.password = password;
        this.fullName = fullName;
        this.role = role;
        this.departmentId = departmentId;
        this.teamId = teamId;
    }

    // Геттеры
    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }

    public String getRole() {
        return role;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public Long getTeamId() {
        return teamId;
    }

    // Сеттеры
    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }
}