package com.example.taskmanager.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private LocalDateTime dueDate;
    private String reminderType; // ONCE, INTERVAL, DAILY, TRIGGER
    private String status; // OPEN, CLOSED, COMPLETED
    private String notificationMethod; // EMAIL, SMS, PUSH
    private int points;

    @ManyToOne
    private User owner;

    @ManyToOne
    private Task triggerTask; // задача-триггер
}
