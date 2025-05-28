package com.example.taskmanage.repository;

import com.example.taskmanage.entity.Role;
import com.example.taskmanage.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByLogin(String login);
    boolean existsByRole(Role role);
    List<User> findByRole(Role role); // Added method to find users by Role entity
    long count(); // ✅ Использует COUNT(*) из таблицы
    @Query("SELECT u FROM User u JOIN FETCH u.role JOIN FETCH u.department JOIN FETCH u.team")
    List<User> findAllWithRelations();
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.role LEFT JOIN FETCH u.department LEFT JOIN FETCH u.team WHERE u.login = :login")
    Optional<User> findByLogin(@Param("login") String login);
    @Query("SELECT u FROM User u WHERE u.role.name = 'TEAM_MEMBER'")
    List<User> findAllTeamMembers();

    @Query("SELECT u FROM User u WHERE u.role.name = 'TEAMLEAD'")
    List<User> findAllTeamLeads();
}