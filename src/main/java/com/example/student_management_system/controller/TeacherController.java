package com.example.student_management_system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.student_management_system.model.Teacher;
import com.example.student_management_system.repository.TeacherRepository;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {

    @Autowired
    private TeacherRepository teacherRepository;

    @GetMapping
    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    @PostMapping
    public Teacher createTeacher(@RequestBody Teacher teacher) {
        if (teacherRepository.existsByEmail(teacher.getEmail())) {
            throw new RuntimeException("Teacher with email " + teacher.getEmail() + " already exists");
        }
        return teacherRepository.save(teacher);
    }

    @GetMapping("/{id}")
    public Teacher getTeacher(@PathVariable Long id) {
        return teacherRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Teacher not found"));
    }
}