package com.example.__HoangAnhThu.controller;

import com.example.__HoangAnhThu.entity.Enrollment;
import com.example.__HoangAnhThu.entity.Student;
import com.example.__HoangAnhThu.repository.EnrollmentRepository;
import com.example.__HoangAnhThu.repository.StudentRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MyCourseController {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;

    public MyCourseController(EnrollmentRepository enrollmentRepository,
                              StudentRepository studentRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
    }

    @GetMapping("/my-courses")
    public String myCourses(Authentication authentication, Model model) {
        String username = authentication.getName();

        Student student = studentRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy student"));

        List<Enrollment> enrollments = enrollmentRepository.findByStudent(student);

        model.addAttribute("enrollments", enrollments);
        return "my-courses";
    }
}