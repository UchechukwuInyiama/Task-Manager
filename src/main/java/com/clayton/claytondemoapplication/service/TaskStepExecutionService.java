package com.clayton.claytondemoapplication.service;

import com.clayton.claytondemoapplication.dto.ApiResponse;
import com.clayton.claytondemoapplication.dto.TaskStepExecutionReportDto;

public interface TaskStepExecutionService {
    ApiResponse addTaskReport(String taskId, TaskStepExecutionReportDto taskStepExecutionReportDto);

    ApiResponse getTaskExecutionStepReportPerTaskId(String taskId);

    ApiResponse getTaskStepReportsByTaskIdOrderedByExecutionTime(String taskId);

    ApiResponse updateTaskStepExecutionReport(String taskStepExecutionId, TaskStepExecutionReportDto taskStepExecutionReportDto);

    ApiResponse terminateTaskStepExecution(String taskStepExecutionId);

    ApiResponse deleteTaskStepExecutionReport(String taskStepExecutionId);
}
