package com.example.demo_rest_api.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

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

    private void calculateCourseAverages(StudentDTO studentDTO) {
        // Calculate average grades
        Map<String, List<Integer>> gradeMap = new HashMap<>();
        for (GradeDTO grade : studentDTO.getGrades()) {
            gradeMap.computeIfAbsent(grade.getCode(), k -> new ArrayList<>()).add(grade.getValue());
        }

        List<GradeDTO> averagedGrades = new ArrayList<>();
        for (Map.Entry<String, List<Integer>> entry : gradeMap.entrySet()) {
            String code = entry.getKey();
            double average = entry.getValue().stream().mapToInt(Integer::intValue).average().orElse(0);
            averagedGrades.add(new GradeDTO(code, (int) average));
        }

        studentDTO.setGrades(averagedGrades);
    }
    
    public Student saveStudent(StudentDTO studentDTO) {
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
        student.setGrades(studentDTO.getGrades().stream().map(gradeDTO -> {
            GradeId gradeId = new GradeId();
            gradeId.setCode(gradeDTO.getCode());
            gradeId.setStdNumber(studentDTO.getStdNumber());

            Grade grade = new Grade();  
            grade.setId(gradeId);
            grade.setValue(gradeDTO.getValue());
            return grade;
        }).toList());

        try {
            return studentRepository.save(student);
        } catch (Exception ex) {
            throw new IllegalArgumentException();
        }
    }

}
