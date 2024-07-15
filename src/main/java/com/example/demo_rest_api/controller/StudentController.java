package com.example.demo_rest_api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo_rest_api.dto.StudentDTO;
import com.example.demo_rest_api.service.StudentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StudentController {
    private final Logger logger = LoggerFactory.getLogger(StudentController.class);        

    private final StudentService studentService;

    // Create Endpoint
    @PostMapping("/v1/student")
    public ResponseEntity<StudentDTO> createNewStudent(@Valid @RequestBody StudentDTO studentDTO) {
        if(studentDTO.getStdNumber() == null) {
            HttpInputMessage httpInputMessage = null;
            throw new HttpMessageNotReadableException("stdNumber field must be provided", httpInputMessage); 
        }

        studentService.createStudent(studentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(studentDTO);
    }

    // Update Endpoint
    @PutMapping("/v1/student/{stdNumber}")
    public ResponseEntity<StudentDTO> updateStudent(@PathVariable String stdNumber, @Valid @RequestBody StudentDTO studentDTO) {
        logger.info("Update request sent by the student: " + stdNumber);

        studentService.updateStudent(stdNumber, studentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(studentDTO);
    }
}
