package com.blusalt.assessment.customerservice.api;

import com.blusalt.assessment.customerservice.dto.ExceptionDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@Slf4j
@ResponseBody
@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler{

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        //to extract the default error message from a diagnostic
        // information about the errors held in MethodArgumentNotValidException
        Exception exception = new Exception(ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        return this.createResponseEntity(HttpStatus.BAD_REQUEST, exception, request);
    }

    private ResponseEntity<Object> createResponseEntity(
            HttpStatus httpStatus, Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, new ExceptionDto(ex.getMessage()),
                new HttpHeaders(), httpStatus, request);
    }


    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<?> handleRuntimeException(RuntimeException e) {
        return new ResponseEntity<>(new ExceptionDto(e.getMessage()), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseEntity<?> handleBadRequestException(Exception e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(new ExceptionDto("An error occurred while processing your request"), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
