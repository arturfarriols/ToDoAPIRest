package com.sacavix.todoapp.persistence.entity;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String title;
    private String description;
    private LocalDateTime createdDate;
    private LocalDateTime expectedTimeArrival;
    private boolean finished;
    private TaskStatus taskStatus;
}
