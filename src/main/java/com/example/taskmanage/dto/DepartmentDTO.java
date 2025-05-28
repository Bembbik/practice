package com.example.taskmanage.dto;

public class DepartmentDTO {
    private Long id;
    private String name;

    public DepartmentDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    // Геттеры
    public Long getId() { return id; }
    public String getName() { return name; }

    // Сеттеры
    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
}