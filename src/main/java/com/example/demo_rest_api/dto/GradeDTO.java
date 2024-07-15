package com.example.demo_rest_api.dto;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageNotReadableException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GradeDTO {

    @NotBlank(message = "'code' field must be provided")
    private String code;

    @NotBlank(message = "'value' field must be provided")
    private int value;

    @JsonCreator
    public GradeDTO(@JsonProperty("code") String code, @JsonProperty("value") int value) {
        if (code != null) {
            this.code = code.toUpperCase();
        } else {
            HttpInputMessage httpInputMessage = null;
            throw new HttpMessageNotReadableException("code field must be provided", httpInputMessage); 
        }

        this.value = value;
    }
}