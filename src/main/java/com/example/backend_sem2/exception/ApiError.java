package com.example.backend_sem2.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ApiError {

    private HttpStatus status;
    private String message;
    private List<String> errors;

    public ApiError(HttpStatus status, String message, List<String> errors) {
        super();
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public ApiError(HttpStatus status, String message, String error) {
        super();
        this.status = status;
        this.message = message;
        errors = Collections.singletonList(error);
    }

    public void addError(CustomErrorException customErrorException){
        if(this.errors == null){
            this.errors = new ArrayList<>();
        }
        errors.add(customErrorException.getMessage());
    }

    public ApiError(List<CustomErrorException> customErrorExceptions){
        this.status = HttpStatus.BAD_REQUEST;
        this.message = "Multiple error!";
        this.errors = customErrorExceptions.stream()
                .map(CustomErrorException::getMessage).collect(Collectors.toList());
    }
}