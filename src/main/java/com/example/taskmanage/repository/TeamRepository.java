package com.example.taskmanage.repository;

import com.example.taskmanage.entity.Department;
import com.example.taskmanage.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Long> {
    List<Team> findByDepartment(Department department);
}
