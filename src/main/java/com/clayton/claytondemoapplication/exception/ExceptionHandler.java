package com.clayton.claytondemoapplication.exception;

import com.clayton.claytondemoapplication.constants.ResponseMessages;
import com.clayton.claytondemoapplication.dto.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class ExceptionHandler extends ResponseEntityExceptionHandler {

    private final String LOGGER_STRING_GET="url ->{} response -> {}";

    ApiResponse apiResponse = ApiResponse.getInstance();

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String>errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x ->x.getDefaultMessage())
                .collect(Collectors.toList());
        ApiResponse res = apiResponse.getFailedResponse(ResponseMessages.FAILED.getCode(),ResponseMessages.FAILED.getMessage() +", "+errors.toString());
        log.error(LOGGER_STRING_GET, null, errors.toString());
        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = ClassNotFoundException.class)
   public ResponseEntity<?> handleHttpClientErrorNotFoundException(ClassNotFoundException ex, HttpHeaders headers, HttpStatus status, HttpServletRequest request) {
        ApiResponse response = apiResponse.getFailedResponse(ResponseMessages.FAILED.getCode(), ex.getMessage());
        log.error(LOGGER_STRING_GET,request.getRequestURL(),ResponseMessages.FAILED.toString());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = HttpClientErrorException.MethodNotAllowed.class)
    public ResponseEntity<?> handleHttpClientMethodNotFoundException(HttpClientErrorException.MethodNotAllowed ex, HttpHeaders headers, HttpStatus status, HttpServletRequest request) {
        ApiResponse response = apiResponse.getFailedResponse(ResponseMessages.FAILED.getCode(),ResponseMessages.FAILED.getMessage());
        log.error(LOGGER_STRING_GET,request.getRequestURL(),ResponseMessages.NOT_ALLOWED.toString());
        return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
    }
    @org.springframework.web.bind.annotation.ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<?> handleHttpClientInternalServerErrorException(RuntimeException ex, HttpServletRequest request) {
        ApiResponse response = apiResponse.getErrorResponse(ResponseMessages.INTERNAL_SERVER_ERROR.getCode(), ex.getMessage());
        log.error(LOGGER_STRING_GET,request.getRequestURL(), ex);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundException(ResourceNotFoundException ex, HttpServletRequest request) {
        ApiResponse response = apiResponse.getNotFoundResponse(ResponseMessages.NOT_FOUND.getCode(),ex.getMessage());
        log.error(LOGGER_STRING_GET,request.getRequestURL(), ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

}
