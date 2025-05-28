package com.example.taskmanage.dto;

public class LoginRequest {

    private String login;
    private String password;

    // Конструктор по умолчанию
    public LoginRequest() {
    }

    // Конструктор со всеми полями
    public LoginRequest(String login, String password) {
        this.login = login;
        this.password = password;
    }

    // Геттеры
    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    // Сеттеры
    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}