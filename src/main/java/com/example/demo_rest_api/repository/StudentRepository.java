package com.example.demo_rest_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo_rest_api.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {
    
}