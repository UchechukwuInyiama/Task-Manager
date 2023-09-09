package com.clayton.claytondemoapplication.service.impl;

import com.clayton.claytondemoapplication.constants.TaskStatus;
import com.clayton.claytondemoapplication.dto.ApiResponse;
import com.clayton.claytondemoapplication.dto.TaskDisplayDto;
import com.clayton.claytondemoapplication.dto.TaskStepExecutionReportDto;
import com.clayton.claytondemoapplication.entity.TaskExecutionReport;
import com.clayton.claytondemoapplication.entity.TaskStepExecutionReport;
import com.clayton.claytondemoapplication.exception.ResourceNotFoundException;
import com.clayton.claytondemoapplication.repository.TaskExecutionReportRepo;
import com.clayton.claytondemoapplication.repository.TaskStepExecutionReportRepo;
import com.clayton.claytondemoapplication.service.TaskStepExecutionService;
import com.clayton.claytondemoapplication.util.MappingUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskStepExecutionServiceImpl implements TaskStepExecutionService {
    private final TaskExecutionReportRepo taskExecutionReportRepo;
    private final TaskStepExecutionReportRepo taskStepExecutionReportRepo;

    ApiResponse apiResponse = ApiResponse.getInstance();

    //creates new Task report
    @Override
    public ApiResponse addTaskReport(String taskId, TaskStepExecutionReportDto taskStepExecutionReportDto){
        TaskExecutionReport report = taskExecutionReportRepo.findByTaskId(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
        TaskStepExecutionReport taskStepExecutionReport = MappingUtil.MapToTaskStepExecutionReport(taskStepExecutionReportDto);
        taskStepExecutionReport.setTaskExecutionId(generateReportId());
        taskStepExecutionReport.setStartDateTime(ZonedDateTime.now());
        taskStepExecutionReport.setErrormessage("Just started, no error yet");
        taskStepExecutionReport.setTaskId(report.getTaskId());
        taskStepExecutionReport.setTaskStatus(String.valueOf(TaskStatus.RUNNING));
        TaskStepExecutionReport save = taskStepExecutionReportRepo.save(taskStepExecutionReport);
        report.setTaskStatus(String.valueOf(TaskStatus.RUNNING));
        report.setTaskStepExecutionReports(new ArrayList<>(List.of(save)));
        TaskDisplayDto displayDto = new TaskDisplayDto(report, List.of(save));
        return apiResponse.getSuccessfulResponse(displayDto);
    }


    //get task executions via taskId
    @Override
    public ApiResponse getTaskExecutionStepReportPerTaskId(String taskId){
        taskExecutionReportRepo.findByTaskId(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task Not Found"));
        return apiResponse.getSuccessfulResponse(taskStepExecutionReportRepo.findAllByTaskId(taskId));
    }

  //this method gets taskSteps via TaskId and orders based on their execution time
    @Override
    public ApiResponse getTaskStepReportsByTaskIdOrderedByExecutionTime (String taskId){
        taskExecutionReportRepo.findByTaskId(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task with TaskId "+ taskId+" not found"));
        return apiResponse.getSuccessfulResponse(taskStepExecutionReportRepo
                .findByTaskIdOrderByExecutionTimeSecondsDesc(taskId));
    }


    //this method updates TaskStepExecutionReport
    @Override
    public ApiResponse updateTaskStepExecutionReport(String taskStepExecutionId, TaskStepExecutionReportDto taskStepExecutionReportDto){
        TaskStepExecutionReport taskStepExecutionReport = taskStepExecutionReportRepo.findByTaskExecutionId(taskStepExecutionId)
                .orElseThrow(() -> new ResourceNotFoundException("TaskStep does not exist"));
        taskStepExecutionReport.setErrormessage(taskStepExecutionReportDto.errormessage());
        taskStepExecutionReport.setTaskStatus(taskStepExecutionReportDto.taskStatus());
        taskStepExecutionReport.setUpdateTime(ZonedDateTime.now());

        if(taskStepExecutionReport.getTaskStatus() == String.valueOf(TaskStatus.SUCCESS)){
            taskStepExecutionReport.setEndDateTime(ZonedDateTime.now());
            long executionTime = ChronoUnit.SECONDS.between(taskStepExecutionReport.getStartDateTime(), taskStepExecutionReport.getEndDateTime());
            taskStepExecutionReport.setExecutionTimeSeconds(executionTime);

        }
        return apiResponse.getSuccessfulResponse(taskStepExecutionReportRepo.save(taskStepExecutionReport));

    }


    //this method terminates task Steps
    @Override
    public ApiResponse terminateTaskStepExecution(String taskStepExecutionId){
        TaskStepExecutionReport taskStepExecutionReport = taskStepExecutionReportRepo.findByTaskExecutionId(taskStepExecutionId)
                .orElseThrow(() -> new ResourceNotFoundException("TaskStep does not exist"));
        taskStepExecutionReport.setEndDateTime(ZonedDateTime.now());
        long executionTime = ChronoUnit.SECONDS.between(taskStepExecutionReport.getStartDateTime(), taskStepExecutionReport.getEndDateTime());
        taskStepExecutionReport.setExecutionTimeSeconds(executionTime);
        return apiResponse.getSuccessfulResponse(taskStepExecutionReportRepo.save(taskStepExecutionReport));
    }


    //this method deletes task steps
    @Override
    public ApiResponse deleteTaskStepExecutionReport(String taskStepExecutionId){
        TaskStepExecutionReport taskStepExecutionReport = taskStepExecutionReportRepo.findByTaskExecutionId(taskStepExecutionId)
                .orElseThrow(() -> new ResourceNotFoundException("TaskStep does not exist"));
        taskStepExecutionReportRepo.delete(taskStepExecutionReport);
        return apiResponse.getSuccessfulResponse("Task Step Deleted successfully");
    }

    // this method generates unique 8 digit alphanumeric
    public String generateReportId(){
        return RandomStringUtils.random(8, true, true)
                .toUpperCase();
    }
}
