package com.clayton.claytondemoapplication.service;

import com.clayton.claytondemoapplication.dto.ApiResponse;
import com.clayton.claytondemoapplication.dto.TaskExecutionReportDto;

public interface TaskExecutionService {
    ApiResponse createTask(TaskExecutionReportDto executionReportDto);

    ApiResponse getTask(String taskExecutionId);

    ApiResponse getTasksByStatus(String taskStatus);

    ApiResponse getTaskExecutionSortedByExecutionTime();

    ApiResponse getAllTaskExecutions();

    ApiResponse updateTaskExecution(String taskExecutionId, TaskExecutionReportDto taskExecutionReportDto);

    ApiResponse terminateTaskExecution(String taskExecutionId);

    ApiResponse deleteTask(String taskExecutionId);
}
