package com.clayton.claytondemoapplication.util;

import com.clayton.claytondemoapplication.dto.TaskExecutionReportDto;
import com.clayton.claytondemoapplication.dto.TaskStepExecutionReportDto;
import com.clayton.claytondemoapplication.entity.TaskExecutionReport;
import com.clayton.claytondemoapplication.entity.TaskStepExecutionReport;

public class MappingUtil {

    public static TaskStepExecutionReport MapToTaskStepExecutionReport(TaskStepExecutionReportDto taskStepExecutionReportDto){
        return new TaskStepExecutionReport(taskStepExecutionReportDto.stepName(), taskStepExecutionReportDto.errormessage());
    }

    public static TaskExecutionReport MapToTaskExecutionReport(TaskExecutionReportDto taskExecutionReportDto){
        return new TaskExecutionReport(taskExecutionReportDto.taskName(), taskExecutionReportDto.errormessage());
    }
}
