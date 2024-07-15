package com.example.demo_rest_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GradeDTO {

    private String code;

    private int value;
}