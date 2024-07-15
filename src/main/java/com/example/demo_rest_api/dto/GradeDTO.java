package com.example.demo_rest_api.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class GradeDTO {

    private String code;

    private int value;

    @JsonCreator
    public GradeDTO(@JsonProperty("code") String code, @JsonProperty("value") int value) {
        this.code = code.toUpperCase();
        this.value = value;
    }
}