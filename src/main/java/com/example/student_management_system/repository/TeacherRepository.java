package com.example.student_management_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.student_management_system.model.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    boolean existsByEmail(String email);
}