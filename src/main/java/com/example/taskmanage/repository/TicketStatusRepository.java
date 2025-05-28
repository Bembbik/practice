package com.example.taskmanage.repository;

import com.example.taskmanage.entity.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TicketStatusRepository extends JpaRepository<TicketStatus, Long> {
    Optional<TicketStatus> findByName(String name);

}
