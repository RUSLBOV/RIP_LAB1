package com.example.student_management_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.student_management_system.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
}