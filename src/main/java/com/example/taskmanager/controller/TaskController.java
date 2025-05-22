package com.example.taskmanager.controller;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.repository.TaskRepository;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskRepository taskRepo;

    public TaskController(TaskRepository taskRepo) {
        this.taskRepo = taskRepo;
    }

    @PostMapping("/create")
    public Task createTask(@RequestBody Task task) {
        return taskRepo.save(task);
    }

    @GetMapping("/user/{userId}")
    public List<Task> getUserTasks(@PathVariable Long userId) {
        return taskRepo.findByOwnerId(userId);
    }

    @PostMapping("/update-status/{taskId}")
    public Task updateStatus(@PathVariable Long taskId, @RequestParam String status) {
        Optional<Task> task = taskRepo.findById(taskId);
        if (task.isPresent()) {
            task.get().setStatus(status);
            return taskRepo.save(task.get());
        }
        throw new RuntimeException("Task not found");
    }
}
