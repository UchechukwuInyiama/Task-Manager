package com.clayton.claytondemoapplication.entity;

import com.clayton.claytondemoapplication.constants.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TaskStepExecutionReport {

    public TaskStepExecutionReport(String stepName, String errormessage) {
        this.stepName = stepName;
        this.errormessage = errormessage;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String taskExecutionId;
    private String taskStatus;
    private String taskId;
    private String stepName;
    private ZonedDateTime startDateTime;
    private ZonedDateTime endDateTime;
    private ZonedDateTime updateTime;
    private Long executionTimeSeconds;
    private String errormessage;


}
