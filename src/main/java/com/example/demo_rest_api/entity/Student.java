package com.example.demo_rest_api.entity;

import java.util.List;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "student")
public class Student {
    
    @Id
    @Column(length = 10)
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "stdNumber must be alphanumeric")
    private String stdNumber;

    @Column(length = 32, nullable = false)
    @Pattern(regexp = "^[A-Za-zğçşıĞÇŞİ]+$", message = "Name must contain valid characters")
    @Size(min = 1, max = 32)
    private String name;

    @Column(length = 32, nullable = false)
    @Pattern(regexp = "^[A-Za-zğçşıĞÇŞİ]+$", message = "Surname must contain valid characters")
    @Size(min = 1, max = 32)
    private String surname;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Grade> grades;
}
