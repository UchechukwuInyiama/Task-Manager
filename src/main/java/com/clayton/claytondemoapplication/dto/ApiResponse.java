package com.clayton.claytondemoapplication.dto;

import com.clayton.claytondemoapplication.constants.ResponseMessages;
import com.clayton.claytondemoapplication.constants.ResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    private ResponseStatus status;
    private String code;
    private String message;
    private T data;

    public ApiResponse(ResponseStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public ApiResponse<T>getSuccessfulResponse(T data){
        return new ApiResponse<>(status.successful, ResponseMessages.SUCCESSFUL.getCode(), ResponseMessages.SUCCESSFUL.getMessage(), data);
    }

    public ApiResponse <T> getFailedResponse(String code, String message){
        return new ApiResponse(status.failed, code, message);
    }

    public ApiResponse <T> getErrorResponse(String code, String message){
        return new ApiResponse(ResponseStatus.error, code, message);
    }

    public ApiResponse <T> getNotFoundResponse(String code,String message){
        return new ApiResponse(ResponseStatus.not_found, code, message);
    }

    public static ApiResponse getInstance() {
        return new ApiResponse();
    }
}
