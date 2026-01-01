package com.example.student_management_system.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.student_management_system.model.Course;
import com.example.student_management_system.model.Enrollment;
import com.example.student_management_system.model.Student;
import com.example.student_management_system.repository.CourseRepository;
import com.example.student_management_system.repository.EnrollmentRepository;
import com.example.student_management_system.repository.StudentRepository;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    @Autowired private EnrollmentRepository enrollmentRepository;
    @Autowired private StudentRepository studentRepository;
    @Autowired private CourseRepository courseRepository;

    // Записать студента на курс
   @PostMapping
public Map<String, Object> enrollStudent(@RequestBody EnrollmentRequest request) {
    Student student = studentRepository.findById(request.getStudentId())
        .orElseThrow(() -> new RuntimeException("Student not found"));
    Course course = courseRepository.findById(request.getCourseId())
        .orElseThrow(() -> new RuntimeException("Course not found"));

    Enrollment enrollment = new Enrollment(student, course);
    enrollment = enrollmentRepository.save(enrollment);

    // Возвращаем только нужное — без связанных коллекций
    return Map.of(
        "id", enrollment.getId(),
        "studentId", student.getId(),
        "courseId", course.getId(),
        "enrolledAt", enrollment.getEnrolledAt()
    );
}

    // Выставить оценку
    @PutMapping("/{id}/grade")
    public Enrollment setGrade(@PathVariable Long id, @RequestBody GradeRequest gradeReq) {
        Enrollment e = enrollmentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Enrollment not found"));
        e.setGrade(gradeReq.getGrade());
        return enrollmentRepository.save(e);
    }

    // Получить все записи студента
    @GetMapping("/student/{studentId}")
    public List<Enrollment> getEnrollmentsByStudent(@PathVariable Long studentId) {
        return enrollmentRepository.findByStudentId(studentId);
    }

    // Успеваемость по курсу
    @GetMapping("/course/{courseId}")
    public List<Enrollment> getGradesByCourse(@PathVariable Long courseId) {
        return enrollmentRepository.findEnrollmentsByCourseId(courseId);
    }

    // DTO для запросов
    public static class EnrollmentRequest {
        private Long studentId;
        private Long courseId;
        // геттеры/сеттеры
        public Long getStudentId() { return studentId; }
        public void setStudentId(Long studentId) { this.studentId = studentId; }
        public Long getCourseId() { return courseId; }
        public void setCourseId(Long courseId) { this.courseId = courseId; }
    }

    public static class GradeRequest {
        private Integer grade;
        public Integer getGrade() { return grade; }
        public void setGrade(Integer grade) { this.grade = grade; }
    }
}