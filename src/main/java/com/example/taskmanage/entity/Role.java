package com.example.taskmanage.entity;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "roles")
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 30)
    private String name;

    // Конструктор по умолчанию
    public Role() {
    }

    // Конструктор с полями id и name
    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    // Конструктор только с name (например, при создании новой роли)
    public Role(String name) {
        this.name = name;
    }

    // Геттеры
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    // Сеттеры
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
