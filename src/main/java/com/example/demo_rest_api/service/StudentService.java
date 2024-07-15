package com.example.demo_rest_api.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.example.demo_rest_api.controller.StudentController;
import com.example.demo_rest_api.dto.GradeDTO;
import com.example.demo_rest_api.dto.StudentDTO;
import com.example.demo_rest_api.entity.Grade;
import com.example.demo_rest_api.entity.Grade.GradeId;
import com.example.demo_rest_api.entity.Student;
import com.example.demo_rest_api.repository.StudentRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class StudentService {
    private final Logger logger = LoggerFactory.getLogger(StudentController.class);   

    private final StudentRepository studentRepository;
    
    private Student convertToEntity(StudentDTO studentDTO) {
        Student student = new Student();
        student.setName(studentDTO.getName());
        student.setSurname(studentDTO.getSurname());
        student.setStdNumber(studentDTO.getStdNumber());

        student.setGrades(studentDTO.getGrades().stream().map(gradeDTO -> {
            GradeId gradeId = new GradeId();
            gradeId.setCode(gradeDTO.getCode());
            gradeId.setStdNumber(studentDTO.getStdNumber());

            Grade grade = new Grade();
            grade.setId(gradeId);
            grade.setValue(gradeDTO.getValue());
            return grade;
        }).toList());

        return student;
    }

    /**
     * Calculates the average grade for each course code in the student's grade list.
     * Updates the student's grade list to only contain the averaged grades.
     *
     * @param studentDTO the student data transfer object containing the grades
     */
    private void calculateCourseAverages(StudentDTO studentDTO) {
        // Map to store course codes and their corresponding list of grades
        Map<String, List<Integer>> gradeMap = new HashMap<>();
        for (GradeDTO grade : studentDTO.getGrades()) {
            // For each grade, add the value to the list associated with its course code in the map
            gradeMap.computeIfAbsent(grade.getCode(), k -> new ArrayList<>()).add(grade.getValue());
        }

        // List to store the averaged grades
        List<GradeDTO> averagedGrades = new ArrayList<>();
        for (Map.Entry<String, List<Integer>> entry : gradeMap.entrySet()) {
            String code = entry.getKey();
            // Calculate the average of the grades for this course code
            double average = entry.getValue().stream().mapToInt(Integer::intValue).average().orElse(0); 
            averagedGrades.add(new GradeDTO(code, (int) average));
        }

        // Update the student's grade list with the averaged grades
        studentDTO.setGrades(averagedGrades);
    }
    
    public Student createStudent(StudentDTO studentDTO) {
        if (studentRepository.existsById(studentDTO.getStdNumber())) {
            logger.info("Exception! Student with stdNumber %s already exists".formatted(studentDTO.getStdNumber()));
            throw new DuplicateKeyException("Student with stdNumber %s already exists".formatted(studentDTO.getStdNumber()));
        }

        calculateCourseAverages(studentDTO);
        
        Student student = convertToEntity(studentDTO);

        try {
            return studentRepository.save(student);
        } catch (Exception ex) {
            throw new IllegalArgumentException();
        }
    }

    public Student updateStudent(String stdNumber, StudentDTO studentDTO) {        
        Student student = studentRepository.findById(stdNumber).orElseThrow(() -> new NoSuchElementException("Student not found"));
        student.setName(studentDTO.getName());
        student.setSurname(studentDTO.getSurname());

        calculateCourseAverages(studentDTO);
        studentDTO.setStdNumber(stdNumber);

        student.setGrades(studentDTO.getGrades().stream().map(gradeDTO -> {
            GradeId gradeId = new GradeId();
            gradeId.setCode(gradeDTO.getCode());
            gradeId.setStdNumber(stdNumber);

            Grade grade = new Grade();  
            grade.setId(gradeId);
            grade.setValue(gradeDTO.getValue());
            return grade;
        }).toList());

        try {
            return studentRepository.save(student);
        } catch (UnsupportedOperationException unsupportedOperationException) {
            try {
                return studentRepository.save(student);
            } catch (Exception ex) {
                throw new IllegalArgumentException();
            }
        }
    }

}
