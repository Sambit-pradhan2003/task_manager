package com.taskmanager.controller;

import com.taskmanager.entity.Task;
import com.taskmanager.entity.User;
import com.taskmanager.payload.TaskDto;
import com.taskmanager.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }
    @PostMapping("/user")
    public ResponseEntity<Task> createTask(@RequestBody TaskDto taskDto, @AuthenticationPrincipal User userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Long userId = userDetails.getId(); // Assuming username stores userId
        Task createdTask = taskService.createTask(taskDto, userId);
        return ResponseEntity.ok(createdTask);
    }


    @GetMapping("/user/uid")
    public ResponseEntity<List<Task>> getTasksByUserId(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(taskService.getTasksByUserId(user.getId()));
    }
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Task>> getTasksByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(taskService.getTasksByCategoryId(categoryId));
    }


    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PutMapping("/tasks/{id}/toggle")
    public ResponseEntity<Task> toggleTaskCompletion(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.toggleTaskCompletion(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task task) {
        return ResponseEntity.ok(taskService.updateTask(id, task));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/search")
    public ResponseEntity<List<Task>> searchTasks(@RequestParam String title,
                                                  @AuthenticationPrincipal User uswerdetail,
                                                  @RequestParam(required = false) String categoryName) {
        return ResponseEntity.ok(taskService.searchTasks(title, uswerdetail.getId(), categoryName));
    }
}
