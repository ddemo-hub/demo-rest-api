package com.example.demo_rest_api.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StudentDTO {

    @NotBlank(message = "'name' field must be provided")
    private String name;

    @NotBlank(message = "'surname' field must be provided")
    private String surname;

    private String stdNumber;

    private List<GradeDTO> grades;
}