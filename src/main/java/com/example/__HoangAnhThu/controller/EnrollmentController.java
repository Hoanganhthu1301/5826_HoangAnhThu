package com.example.__HoangAnhThu.controller;

import com.example.__HoangAnhThu.entity.Course;
import com.example.__HoangAnhThu.entity.Enrollment;
import com.example.__HoangAnhThu.entity.Student;
import com.example.__HoangAnhThu.repository.CourseRepository;
import com.example.__HoangAnhThu.repository.EnrollmentRepository;
import com.example.__HoangAnhThu.repository.StudentRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
public class EnrollmentController {

    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;

    public EnrollmentController(CourseRepository courseRepository,
                                EnrollmentRepository enrollmentRepository,
                                StudentRepository studentRepository) {
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
    }

    @GetMapping("/enroll/{courseId}")
    public String enrollCourse(@PathVariable Long courseId,
                               Authentication authentication,
                               RedirectAttributes redirectAttributes) {

        String username = authentication.getName();

        Student student = studentRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy student"));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy course"));

        if (enrollmentRepository.existsByStudentAndCourse(student, course)) {
            redirectAttributes.addFlashAttribute("error", "Bạn đã đăng ký học phần này rồi");
            return "redirect:/";
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setEnrollDate(LocalDate.now());

        enrollmentRepository.save(enrollment);

        redirectAttributes.addFlashAttribute("success", "Đăng ký học phần thành công");
        return "redirect:/my-courses";
    }
}