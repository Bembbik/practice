package com.example.taskmanage.repository;

import com.example.taskmanage.entity.TicketPriorityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketPriorityRepository extends JpaRepository<TicketPriorityEntity, Long> {
}