package com.example.taskmanager.dto;

import com.example.taskmanager.model.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TaskEvent {

    private String eventType;   // CREATED, UPDATED, MOVED
    private Long taskId;
    private String title;
    private TaskStatus status;
    private Long projectId;
    private Long assignedUserId;
}
