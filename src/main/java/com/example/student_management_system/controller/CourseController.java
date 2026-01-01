package com.example.student_management_system.controller;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody; 
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.student_management_system.model.Course;
import com.example.student_management_system.model.Teacher;
import com.example.student_management_system.repository.CourseRepository;
import com.example.student_management_system.repository.TeacherRepository;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;
    
    @Autowired
    private TeacherRepository teacherRepository; 

    @GetMapping
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

   @PostMapping
public Course createCourse(@RequestBody CourseRequest request) {
    Optional<Teacher> teacherOpt = teacherRepository.findById(request.getTeacherId());
if (teacherOpt.isEmpty()) {
    throw new ResponseStatusException(HttpStatus.NOT_FOUND,
        "Teacher with id " + request.getTeacherId() + " not found");
}
Teacher teacher = teacherOpt.get();
    
    Course course = new Course(request.getTitle(), request.getCode());
    course.setTeacher(teacher);
    return courseRepository.save(course);
}

    @GetMapping("/{id}")
    public Course getCourse(@PathVariable Long id) {
        return courseRepository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public Course updateCourse(@PathVariable Long id, @RequestBody Course courseDetails) {
        return courseRepository.findById(id)
            .map(course -> {
                course.setTitle(courseDetails.getTitle());
                course.setCode(courseDetails.getCode());
                if (courseDetails.getTeacher() != null) {
                    course.setTeacher(courseDetails.getTeacher());
                }
                return courseRepository.save(course);
            })
            .orElse(null);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        if (!courseRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        courseRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    public static class CourseRequest {
        private String title;
        private String code;
        private Long teacherId;

        // Геттеры и сеттеры
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        public String getCode() { return code; }
        public void setCode(String code) { this.code = code; }
        public Long getTeacherId() { return teacherId; }
        public void setTeacherId(Long teacherId) { this.teacherId = teacherId; }
    }
}