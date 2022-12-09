package com.sacavix.todoapp.service;

import com.sacavix.todoapp.exception.ToDoExceptions;
import com.sacavix.todoapp.mapper.TaskInDTOToTask;
import com.sacavix.todoapp.persistence.entity.Task;
import com.sacavix.todoapp.persistence.entity.TaskStatus;
import com.sacavix.todoapp.persistence.repository.TaskRepository;
import com.sacavix.todoapp.service.dto.TaskInDTO;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.Transient;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskInDTOToTask taskMapper;

    public TaskService(TaskRepository taskRepository, TaskInDTOToTask taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    public Task createTask(TaskInDTO taskInDTO) {
        createTaskErrorTreatment(taskInDTO);
        Task task = taskMapper.map(taskInDTO);
        return this.taskRepository.save(task);
    }

    public List<Task> findAll() {
        return this.taskRepository.findAll();
    }

    public List<Task> findAllByTaskStatus(TaskStatus taskStatus) {
        return this.taskRepository.findAllByTaskStatus(taskStatus);
    }

    @Transactional
    public void updateTaskFinished(Long id) {
        Optional<Task> optionalTask = this.taskRepository.findById(id);
        if (optionalTask.isEmpty()) {
            throw new ToDoExceptions("Task not found", HttpStatus.NOT_FOUND);
        }


        Task task = optionalTask.get();

        updateTaskFinishedErrorTreatment(task);

        task.setFinished(true);
        if (optionalTask.get().getExpectedTimeArrival().isBefore(LocalDateTime.now())) {
            task.setTaskStatus(TaskStatus.LATE);
        }
        this.taskRepository.save(task);


    }

    public void deleteTaskById(Long id) {
        Optional<Task> optionalTask = this.taskRepository.findById(id);
        if (optionalTask.isEmpty()) {
            throw new ToDoExceptions("Task not found", HttpStatus.NOT_FOUND);
        }
        this.taskRepository.deleteById(id);
    }

    public void updateTaskFinishedErrorTreatment(Task task) {
        if (task.isFinished()) {
            throw new ToDoExceptions("Task already finished", HttpStatus.CONFLICT);
        }
    }

    public void createTaskErrorTreatment (TaskInDTO taskInDTO) {
        String title = taskInDTO.getTitle();
        LocalDateTime date = taskInDTO.getExpectedTimeArrival();
        Optional<Task> alreadyExistingTask = taskRepository.findByTitle(title);
        if (alreadyExistingTask.isPresent()) {
            throw new ToDoExceptions("There is already an exiting task with the title: " + title, HttpStatus.CONFLICT);
        }
        if (date.isBefore(LocalDateTime.now())) {
            throw new ToDoExceptions("Invalid date", HttpStatus.CONFLICT);
        }
    }
}
