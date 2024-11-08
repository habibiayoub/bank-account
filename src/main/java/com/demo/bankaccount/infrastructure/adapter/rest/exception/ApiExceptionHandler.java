package com.demo.bankaccount.infrastructure.adapter.rest.exception;

import com.demo.bankaccount.utils.exceptions.InvalidOperationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {InvalidOperationException.class})
    public ResponseEntity<Object> handleApiRequestException(InvalidOperationException e) {
        ApiException apiException = new ApiException(e.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(apiException, apiException.getStatus());
    }

}
