package com.example.demo_rest_api.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class StudentDTO {

    @NotBlank(message = "'name' field must be provided")
    private String name;

    @NotBlank(message = "'surname' field must be provided")
    private String surname;

    private String stdNumber;

    private List<GradeDTO> grades;

    @JsonCreator
    public StudentDTO(
        @JsonProperty("name") String name,
        @JsonProperty("surname") String surname,
        @JsonProperty("stdNumber") String stdNumber,
        @JsonProperty("grades") List<GradeDTO> grades
    ) {
        // Name and Surname fields have their first letters capitalized
        this.name = capitalizeFirstLetter(name);
        this.surname = capitalizeFirstLetter(surname);
        
        // Grades and stdNumber fields are all uppercase
        if (this.stdNumber != null) {
            this.stdNumber = stdNumber.toUpperCase();
        } else {
            this.stdNumber = stdNumber;
        }
        this.grades = grades;
    }

    private String capitalizeFirstLetter(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }
}