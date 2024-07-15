package com.example.demo_rest_api.advice;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.demo_rest_api.dto.ErrorDTO;

/** Global exception handler */
@RestControllerAdvice
public class ApplicationExceptionHandler {
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorDTO> handleInvalidRequest(HttpMessageNotReadableException ex) {
        ErrorDTO responseDTO = ErrorDTO.builder()
                                .status(400)
                                .error("BAD REQUEST")
                                .message(ex.getMessage())
                                .build();

        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorDTO> handleStudentNotFound(NoSuchElementException ex) {
        ErrorDTO responseDTO = ErrorDTO.builder()
                                .status(404)
                                .error("NOT FOUND")
                                .message(ex.getMessage())
                                .build();

        return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDTO> handleInvalidValue(IllegalArgumentException ex) {
        String message = "Please respect the following constraints: 1) 'stdNumber' field must be alphanumerical, 2) 'stdNumber' must be 10 characters long, " +
                         "3) 'name' and 'surname' fields must be alphabetical, 4) 'name' and 'surname' fields must be less than 32 characters " + 
                         "5) A course 'code' is compromised of 2 or 3 letters followed by 3 numbers";
        
        ErrorDTO responseDTO = ErrorDTO.builder()
                                .status(400)
                                .error("BAD REQUEST")
                                .message(message)
                                .build();

        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }
}
