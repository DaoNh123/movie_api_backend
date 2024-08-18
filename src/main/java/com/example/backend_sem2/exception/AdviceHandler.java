package com.example.backend_sem2.exception;

import com.example.backend_sem2.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.resource.NoResourceFoundException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class AdviceHandler {
    @ExceptionHandler(CustomErrorException.class)
    public ResponseEntity<ErrorResponse>  catchError(Exception ex){
        CustomErrorException customErrorException = (CustomErrorException) ex;
        HttpStatus status = customErrorException.getStatus();

        ErrorResponse errorResponse = new ErrorResponse(status,
                customErrorException.getMessage());

        return new ResponseEntity<>(errorResponse, status);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> catchMethodArgumentNotValidException(
            MethodArgumentNotValidException ex
    ) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(new ErrorResponse(HttpStatus.BAD_REQUEST, "Multiple error!", null, errors));
    }


    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> catchOtherException(NoResourceFoundException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        System.out.println(ex.getClass() + "***");
        ErrorResponse errorResponse = new ErrorResponse(status,
                ex.getMessage());
        System.out.println(ex.getClass() + "***");


        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public String handleHttpMediaTypeNotAcceptableException() {
        return "acceptable MIME type:" + MediaType.APPLICATION_JSON_VALUE;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> catchOtherException(Exception ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        System.out.println(ex.getClass() + "***");
        System.out.println(ex.getMessage());
        ex.printStackTrace();
        ErrorResponse errorResponse = new ErrorResponse(status,
                ex.getMessage());


        return new ResponseEntity<>(errorResponse, status);
    }
}
