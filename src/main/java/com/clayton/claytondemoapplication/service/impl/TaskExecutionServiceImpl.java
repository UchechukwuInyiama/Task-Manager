package com.clayton.claytondemoapplication.service.impl;

import com.clayton.claytondemoapplication.constants.TaskStatus;
import com.clayton.claytondemoapplication.constants.TaskStatusMessages;
import com.clayton.claytondemoapplication.dto.ApiResponse;
import com.clayton.claytondemoapplication.dto.TaskDisplayDto;
import com.clayton.claytondemoapplication.dto.TaskExecutionReportDto;
import com.clayton.claytondemoapplication.entity.TaskExecutionReport;
import com.clayton.claytondemoapplication.entity.TaskStepExecutionReport;
import com.clayton.claytondemoapplication.exception.ResourceNotFoundException;
import com.clayton.claytondemoapplication.repository.TaskExecutionReportRepo;
import com.clayton.claytondemoapplication.repository.TaskStepExecutionReportRepo;
import com.clayton.claytondemoapplication.service.TaskExecutionService;
import com.clayton.claytondemoapplication.util.MappingUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class TaskExecutionServiceImpl implements TaskExecutionService {
    ApiResponse apiResponse = ApiResponse.getInstance();

    private final TaskExecutionReportRepo taskExecutionReportRepo;

    private final TaskStepExecutionReportRepo taskStepExecutionReportRepo;

    //This method creates new TaskExecutions
    @Override
    public ApiResponse createTask(TaskExecutionReportDto executionReportDto) {
        TaskExecutionReport executionReport = MappingUtil.MapToTaskExecutionReport(executionReportDto);
        executionReport.setStartDateTime(ZonedDateTime.now());
        executionReport.setTaskId(generateTaskId());
        executionReport.setTaskStatus(String.valueOf(TaskStatus.STARTED_NO_REPORTS_YET));
        executionReport.setErrormessage(String.valueOf(TaskStatusMessages.NO_ERRORS_YET));
        return apiResponse.getSuccessfulResponse(taskExecutionReportRepo.save(executionReport));
    }


    //This method generates an 8 digit unique alphanumeric which will be assigned as taskID to all tasks created
    private String generateTaskId() {
        return RandomStringUtils.random(8, true, true)
                .toUpperCase();
    }

    //This method fetches a single task based on the taskExecutionId provided
    @Override
    public ApiResponse getTask(String taskExecutionId) {
        updateTaskStatus(taskExecutionId);
        TaskDisplayDto displayDto = new TaskDisplayDto(taskExecutionReportRepo.findByTaskId(taskExecutionId)
                .orElseThrow(() -> new ResourceNotFoundException("Task Not Found")),
                taskStepExecutionReportRepo.findAllByTaskId(taskExecutionId).stream().collect(Collectors.toList()));
        return apiResponse.getSuccessfulResponse(displayDto);
    }


    //This method fetches all tasks in the database via their status
    @Override
    public ApiResponse getTasksByStatus(String taskStatus){
        return apiResponse.getSuccessfulResponse(taskExecutionReportRepo.findAllByTaskStatus(taskStatus));
    }


    //This method fetches all tasks and sorts them via their execution time
    @Override
    public ApiResponse getTaskExecutionSortedByExecutionTime(){
        return apiResponse.getSuccessfulResponse(taskExecutionReportRepo.findAllByOrderByExecutionTimeSecondsDesc());
    }


    //This method fetches all tasks available in the database
    @Override
    public ApiResponse getAllTaskExecutions(){

        return apiResponse.getSuccessfulResponse(taskExecutionReportRepo.findAll());
    }


    //This method updates the task status taking into consideration the status of the steps associated with them
    private void updateTaskStatus(String taskExecutionId) {
        TaskExecutionReport report = taskExecutionReportRepo.findByTaskId(taskExecutionId)
                .orElseThrow(() -> new ResourceNotFoundException("Task Not Found"));
        List<TaskStepExecutionReport> stepReports = taskStepExecutionReportRepo.findAllByTaskId(taskExecutionId);
            if (stepReports.stream().anyMatch(report1 -> report1.getTaskStatus().equalsIgnoreCase("FAILURE"))) {
                report.setTaskStatus(String.valueOf(TaskStatus.FAILURE));
                report.setErrormessage(String.valueOf(TaskStatusMessages.TASK_FAILED));
            } else if (stepReports.stream().anyMatch(report1 -> report1.getTaskStatus().equalsIgnoreCase("RUNNING"))) {
                report.setTaskStatus(String.valueOf(TaskStatus.RUNNING));
                report.setErrormessage(String.valueOf(TaskStatusMessages.TASK_IS_RUNNING));
            } else {
                report.setTaskStatus(String.valueOf(TaskStatus.SUCCESS));
                report.setErrormessage(String.valueOf(TaskStatusMessages.TASK_SUCCESSFUL));
            }

            taskExecutionReportRepo.save(report);
        }

        
        //This method updates the task contents
    @Override
    public ApiResponse updateTaskExecution(String taskExecutionId, TaskExecutionReportDto taskExecutionReportDto) {
        TaskExecutionReport taskExecutionReport = taskExecutionReportRepo.findByTaskId(taskExecutionId)
                .orElseThrow(() -> new ResourceNotFoundException("Task with ID " + taskExecutionId + " not found"));
        if(taskExecutionReportDto.taskName() != null){
            taskExecutionReport.setTaskName(taskExecutionReportDto.taskName());
        }

        taskExecutionReport.setErrormessage(taskExecutionReportDto.errormessage());
        return apiResponse.getSuccessfulResponse(taskExecutionReportRepo.save(taskExecutionReport));
    }


    //This method terminates tasks
    @Override
    public ApiResponse terminateTaskExecution(String taskExecutionId) {
        TaskExecutionReport taskExecutionReport = taskExecutionReportRepo.findByTaskId(taskExecutionId)
                .orElseThrow(() -> new ResourceNotFoundException("Task with ID " + taskExecutionId + " not found"));
        taskExecutionReport.setEndDateTime(ZonedDateTime.now());
        long executionTime = ChronoUnit.SECONDS.between(taskExecutionReport.getStartDateTime(), taskExecutionReport.getEndDateTime());
        taskExecutionReport.setExecutionTimeSeconds(executionTime);
        updateTaskStatus(taskExecutionId);
        return apiResponse.getSuccessfulResponse(taskExecutionReportRepo.save(taskExecutionReport));
    }


    //This method deletes tasks
    @Override
    public ApiResponse deleteTask(String taskExecutionId){
        TaskExecutionReport taskExecutionReport = taskExecutionReportRepo.findByTaskId(taskExecutionId)
                .orElseThrow(() -> new ResourceNotFoundException("Task with ID " + taskExecutionId + " not found"));
       taskExecutionReportRepo.delete(taskExecutionReport);
       taskStepExecutionReportRepo.deleteAll(taskStepExecutionReportRepo.findAllByTaskId(taskExecutionId));

        return apiResponse.getSuccessfulResponse("Task Deleted Successfully");
    }
}
