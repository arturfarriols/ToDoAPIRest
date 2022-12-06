package com.sacavix.todoapp.mapper;

import com.sacavix.todoapp.persistence.entity.Task;
import com.sacavix.todoapp.persistence.entity.TaskStatus;
import com.sacavix.todoapp.service.dto.TaskInDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TaskInDTOToTask implements IMapper<TaskInDTO, Task>{
    @Override
    public Task map(TaskInDTO in) {
        Task task = new Task();

        task.setTitle(in.getTitle());
        task.setDescription(in.getDescription());
        task.setExpectedTimeArrival(in.getExpectedTimeArrival());
        task.setFinished(false);
        task.setTaskStatus(TaskStatus.ON_TIME);
        task.setCreatedDate(LocalDateTime.now());

        return task;
    }
}
