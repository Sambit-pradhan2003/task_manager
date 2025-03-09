package com.taskmanager.service;

import com.taskmanager.entity.Category;
import com.taskmanager.entity.Task;
import com.taskmanager.entity.User;
import com.taskmanager.payload.TaskDto;
import com.taskmanager.repository.CategoryRepository;
import com.taskmanager.repository.TaskRepository;
import com.taskmanager.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    public final UserRepository userRepository;
    public  final CategoryRepository categoryRepository;

    public TaskService(TaskRepository taskRepository,UserRepository userRepository,CategoryRepository categoryRepository) {
        this.taskRepository = taskRepository;
        this.userRepository=userRepository;
        this.categoryRepository = categoryRepository;
    }

    public Task createTask(TaskDto taskDTO, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Category category = categoryRepository.findByName(taskDTO.getCategoryName())
                .orElseGet(() -> {
                    Category newCategory = new Category();
                    newCategory.setName(taskDTO.getCategoryName());
                    return categoryRepository.save(newCategory);
                });

        Task task = new Task();
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setCompleted(taskDTO.isCompleted());
        task.setUser(user);
        task.setCategory(category);
        task.setCreatedAt(LocalDateTime.now());
        task.setUpdatedAt(LocalDateTime.now());

        return taskRepository.save(task);
    }
    public List<Task> getTasksByUserId(Long userId) {
        return taskRepository.findByUserId(userId);
    }

    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    public Task updateTask(Long id, Task updatedTask) {
        return taskRepository.findById(id).map(task -> {
            task.setTitle(updatedTask.getTitle());
            task.setDescription(updatedTask.getDescription());
            task.setCompleted(updatedTask.isCompleted());
            task.setUpdatedAt(LocalDateTime.now());
            return taskRepository.save(task);
        }).orElseThrow(() -> new RuntimeException("Task not found"));
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public List<Task> getTasksByCategoryId(Long categoryId) {
        return  taskRepository.findByCategoryId(categoryId);
    }

    public List<Task> searchTasks(String title, Long userId, String name) {
        if (title == null || title.isEmpty()) {
            title = null; // Ensure it's handled properly
        }
        return taskRepository.searchTasks(title, userId, name);
    }


    public Task toggleTaskCompletion(Long id) {
        return taskRepository.findById(id).map(task -> {
            task.setCompleted(!task.isCompleted()); // ✅ Toggle completion status
            task.setUpdatedAt(LocalDateTime.now());
            return taskRepository.save(task);
        }).orElseThrow(() -> new RuntimeException("Task not found"));
    }
}
