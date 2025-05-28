package com.example.taskmanage.repository;

import com.example.taskmanage.entity.Ticket;
import com.example.taskmanage.entity.Team;
import com.example.taskmanage.enums.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findByAssignedTeam_Id(Long teamId);
    List<Ticket> findByAssignedTeamlead_Id(Long teamleadId);
    List<Ticket> findByDepartment_Id(Long departmentId);
    List<Ticket> findByAssignedTeam(Team team);
    List<Ticket> findByStatus(TicketStatus status);

    // Подсчёт тикетов по статусу
    Long countByStatus(TicketStatus status);

    // Список тикетов отдела по статусу
    List<Ticket> findByDepartment_IdAndStatus(Long departmentId, TicketStatus status);

    void deleteById(Long id); // Если ещё нет
    // Общее количество тикетов
    long count();


    @Query("SELECT COUNT(t) FROM Ticket t WHERE t.assignedTeam.department.id = :departmentId AND t.priority = 'HIGH'")
    long countHighPriorityByDepartmentId(@Param("departmentId") Long departmentId);

    @Query("SELECT COUNT(t) FROM Ticket t WHERE t.assignedTeam.department.id = :departmentId")
    long countTotalByDepartmentId(@Param("departmentId") Long departmentId);

    @Query("SELECT COUNT(t) FROM Ticket t WHERE t.assignedTeam.department.id = :departmentId AND t.status = 'COMPLETED'")
    long countCompletedByDepartmentId(@Param("departmentId") Long departmentId);
}