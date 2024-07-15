package com.example.demo_rest_api.entity;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "grade")
public class Grade {

    @EmbeddedId
    private GradeId id;

    private int value;

    @ManyToOne
    @JoinColumn(name = "stdNumber", insertable = false, updatable = false)
    private Student student;

    @Embeddable 
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GradeId implements Serializable {
        
        private String code;
        private String stdNumber;
    }
}