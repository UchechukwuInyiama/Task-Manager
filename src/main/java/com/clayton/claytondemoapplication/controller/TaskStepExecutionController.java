package com.clayton.claytondemoapplication.controller;

import com.clayton.claytondemoapplication.dto.TaskStepExecutionReportDto;
import com.clayton.claytondemoapplication.service.TaskStepExecutionService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/taskStepExecution")
public class TaskStepExecutionController {

    private final TaskStepExecutionService taskStepExecutionService;

    @ApiOperation(value = "add task steps executions to existing tasks", notes = " this endpoint adds task steps executions to tasks. The task ID is provided which " +
            " fetches the task. A TaskStepExecutionDTO is passed to add details on the task steps to be added")
    @PostMapping("/addTaskStep/{taskId}")
    public ResponseEntity<?>addTaskReport(@PathVariable("taskId") String taskId, @RequestBody TaskStepExecutionReportDto taskStepExecutionReportDto){
        return ResponseEntity.ok(taskStepExecutionService.addTaskReport(taskId, taskStepExecutionReportDto));
    }

    @ApiOperation(value = "updates existing taskSteps", notes = " this endpoint fetches the task Step to be updated via the taskStepExecutionID provided" +
            ". The data fetched is then updated via the TaskStepExecutionReportDto passed ")
    @PutMapping("/updateTaskStep/{taskStepExecutionId}")
    public ResponseEntity<?>updateTaskStepExecutionReport(@PathVariable("taskStepExecutionId") String taskStepExecutionId, @RequestBody TaskStepExecutionReportDto taskStepExecutionReportDto){
        return ResponseEntity.ok(taskStepExecutionService.updateTaskStepExecutionReport(taskStepExecutionId, taskStepExecutionReportDto));
    }

    @ApiOperation(value = "deletes existing taskSteps", notes = "this endpoint fetches the taskStep via the  taskStepExecutionID and deletes it. The related Task will not be deleted")
    @DeleteMapping("/deleteTaskStep/{taskStepExecutionId}")
    public ResponseEntity<?>deleteTaskStepExecutionById(@PathVariable("taskStepExecutionId") String taskStepExecutionId){
        return ResponseEntity.ok(taskStepExecutionService.deleteTaskStepExecutionReport(taskStepExecutionId));
    }

    @ApiOperation(value = "terminates existing taskSteps", notes = " The taskStepExecutionID is passed, the task Steps associated with the ID is fetched and terminated. This works by passing an endtime value to the task")
    @GetMapping("/terminateTaskStep/{taskStepExecutionId}")
    public ResponseEntity<?>terminateTaskStepById(@PathVariable("taskStepExecutionId") String taskStepExecutionId){
        return ResponseEntity.ok(taskStepExecutionService.terminateTaskStepExecution(taskStepExecutionId));
    }

    @ApiOperation(value = "fetches all task steps associated with Task and displays based on their execution time",
            notes = "This endpoint fetches all the task steps related to a task. The task steps are ordered based on the execution time in descending order. The taskExecutionID is" +
                    " passed which fetches all task steps associated with it")
    @GetMapping("/task-step-reportsSortedByExecutionTime/{taskExecutionId}")
    public ResponseEntity<?>getAllTaskStepsByTaskIdSortedByExecutionTime(@PathVariable("taskExecutionId") String taskExecutionId){
        return ResponseEntity.ok(taskStepExecutionService.getTaskStepReportsByTaskIdOrderedByExecutionTime(taskExecutionId));
    }

    @ApiOperation(value = "gets all task steps associated with a specific task", notes = " This endpoint fetches all the task steps related to a task. The taskExecution Id is passed which fetches all relates task steps")
    @GetMapping("/taskStepsReport/{taskExecutionId}")
    public ResponseEntity<?> getTaskStepReportsByTaskId(@PathVariable("taskExecutionId") String taskExecutionId){
        return ResponseEntity.ok( taskStepExecutionService.getTaskExecutionStepReportPerTaskId(taskExecutionId));
    }
}
