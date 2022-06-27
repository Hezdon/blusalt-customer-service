package com.blusalt.assessment.customerservice.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;


@Slf4j
@ResponseBody
@ControllerAdvice(annotations = RestController.class, basePackages = "com.blusalt.assessment.customerservice.api")
public class ExceptionHandlerController {

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<?> handleBadRequestException(RuntimeException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<?> handleCustomValidationException(MethodArgumentNotValidException e) {

        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseEntity<?> handleBadRequestException(Exception e) {
        log.error(e.getMessage());
        return new ResponseEntity<>("An error occurred while processing your request", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
