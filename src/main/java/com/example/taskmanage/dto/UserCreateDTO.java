package com.example.taskmanage.dto;

public class UserCreateDTO {

    private String login;
    private String fullName;
    private String role; // Например: ADMIN, DEPARTMENT, TEAMLEAD, TEAM
    private Long departmentId; // для выбора существующего отдела
    private String newDepartmentName; // для создания нового отдела
    private String password; // пароль вводит админ

    // Геттеры и сеттеры

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getNewDepartmentName() {
        return newDepartmentName;
    }

    public void setNewDepartmentName(String newDepartmentName) {
        this.newDepartmentName = newDepartmentName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}