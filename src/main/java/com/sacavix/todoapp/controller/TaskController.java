package com.sacavix.todoapp.controller;

import com.sacavix.todoapp.persistence.entity.Task;
import com.sacavix.todoapp.persistence.entity.TaskStatus;
import com.sacavix.todoapp.service.TaskService;
import com.sacavix.todoapp.service.dto.TaskInDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.List;

@RestController
@RequestMapping("/tasks") //plural
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @ApiOperation(value = "CREATE TASK")
    @PostMapping
    public Task createTask(@RequestBody TaskInDTO taskInDTO) {
        return this.taskService.createTask(taskInDTO);
    }

    @ApiOperation(value = "FIND ALL TASKS")
    @GetMapping
    public List<Task> findAll() {
        return this.taskService.findAll();
    }

    @ApiOperation(value = "FIND TASKS BY STATUS")
    @GetMapping("/status/{status}")
    public List<Task> findAllByStatus(@PathVariable("status") TaskStatus status) {
        return this.taskService.findAllByTaskStatus(status);
    }

    @ApiOperation(value = "MARK TASK AS FINISHED")
    @PutMapping ("/markasfinished/{id}")
    public ResponseEntity<Void> markAsFinished(@PathVariable("id")Long id) {
        this.taskService.updateTaskFinished(id);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "DELETE TASKS")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id")Long id) {
        this.taskService.deleteTaskById(id);
        return ResponseEntity.noContent().build();
    }
}
