package com.example.student_management_system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.student_management_system.model.Enrollment;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    List<Enrollment> findByStudentId(Long studentId);
    List<Enrollment> findByCourseId(Long courseId);

    // Успеваемость по курсу: студент + оценка
    @Query("SELECT e FROM Enrollment e WHERE e.course.id = :courseId ORDER BY e.student.name")
    List<Enrollment> findEnrollmentsByCourseId(@Param("courseId") Long courseId);
}