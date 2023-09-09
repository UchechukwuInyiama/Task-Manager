package com.clayton.claytondemoapplication.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TaskExecutionReport {

    public TaskExecutionReport(String taskName, String errormessage) {
        this.taskName = taskName;
        this.errormessage = errormessage;
    }

    public TaskExecutionReport(Long id, String taskId, String taskName, String taskStatus, ZonedDateTime startDateTime, ZonedDateTime endDateTime, Long executionTimeSeconds, String errormessage) {
        this.id = id;
        this.taskId = taskId;
        this.taskName = taskName;
        this.taskStatus = taskStatus;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.executionTimeSeconds = executionTimeSeconds;
        this.errormessage = errormessage;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String taskId;
    private String taskName;
    private String taskStatus;
    private ZonedDateTime startDateTime;
    private ZonedDateTime endDateTime;
    private Long executionTimeSeconds;
    private String errormessage;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, orphanRemoval = true)

    private List<TaskStepExecutionReport> taskStepExecutionReports = new ArrayList<>();
}
