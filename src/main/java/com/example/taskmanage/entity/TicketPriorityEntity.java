package com.example.taskmanage.entity;

import com.example.taskmanage.enums.TicketPriority;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ticket_priorities")
public class TicketPriorityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name; // Например: "LOW", "MEDIUM"

    public TicketPriorityEntity(TicketPriority priority) {
        this.name = priority.name();
    }
}