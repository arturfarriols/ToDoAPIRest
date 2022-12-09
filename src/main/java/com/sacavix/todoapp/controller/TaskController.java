package com.sacavix.todoapp.controller;

import com.sacavix.todoapp.persistence.entity.Task;
import com.sacavix.todoapp.persistence.entity.TaskStatus;
import com.sacavix.todoapp.service.TaskService;
import com.sacavix.todoapp.service.dto.TaskInDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.List;

import static java.net.HttpURLConnection.*;

@RestController
@RequestMapping("/tasks") //plural
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @ApiOperation(value = "CREATE TASK")
    @ApiResponses({
            @ApiResponse(code = HTTP_OK, message = "Task"),
            @ApiResponse(code = HTTP_CONFLICT, message = "Conflict"),
    })
    @PostMapping
    public Task createTask(@RequestBody TaskInDTO taskInDTO) {
        return this.taskService.createTask(taskInDTO);
    }

    @ApiOperation(value = "FIND ALL TASKS")
    @ApiResponses({
            @ApiResponse(code = HTTP_OK, message = "Tasks"),
    })
    @GetMapping
    public List<Task> findAll() {
        return this.taskService.findAll();
    }

    @ApiOperation(value = "FIND TASKS BY STATUS")
    @ApiResponses({
            @ApiResponse(code = HTTP_OK, message = "Task"),
    })
    @GetMapping("/status/{status}")
    public List<Task> findAllByStatus(@PathVariable("status") TaskStatus status) {
        return this.taskService.findAllByTaskStatus(status);
    }

    @ApiOperation(value = "MARK TASK AS FINISHED")
    @ApiResponses({
            @ApiResponse(code = HTTP_NO_CONTENT, message = "Task"),
            @ApiResponse(code = HTTP_CONFLICT, message = "Conflict"),
    })
    @PutMapping ("/markasfinished/{id}")
    public ResponseEntity<Void> markAsFinished(@PathVariable("id")Long id) {
        this.taskService.updateTaskFinished(id);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "DELETE TASKS")
    @ApiResponses({
            @ApiResponse(code = HTTP_NO_CONTENT, message = "Task"),
            @ApiResponse(code = HTTP_CONFLICT, message = "Conflict"),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id")Long id) {
        this.taskService.deleteTaskById(id);
        return ResponseEntity.noContent().build();
    }
}
