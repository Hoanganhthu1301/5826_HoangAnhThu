package com.example.__HoangAnhThu.controller;

import com.example.__HoangAnhThu.entity.Role;
import com.example.__HoangAnhThu.entity.Student;
import com.example.__HoangAnhThu.repository.RoleRepository;
import com.example.__HoangAnhThu.repository.StudentRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    private final StudentRepository studentRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(StudentRepository studentRepository,
                          RoleRepository roleRepository,
                          PasswordEncoder passwordEncoder) {
        this.studentRepository = studentRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("student", new Student());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("student") Student student,
                           RedirectAttributes redirectAttributes) {

        System.out.println("=== DANG KY BAT DAU ===");
        System.out.println("Username: " + student.getUsername());
        System.out.println("Email: " + student.getEmail());

        if (studentRepository.existsByUsername(student.getUsername())) {
            System.out.println("Trung username");
            redirectAttributes.addFlashAttribute("error", "Username đã tồn tại");
            return "redirect:/register";
        }

        if (studentRepository.existsByEmail(student.getEmail())) {
            System.out.println("Trung email");
            redirectAttributes.addFlashAttribute("error", "Email đã tồn tại");
            return "redirect:/register";
        }

        Role roleStudent = roleRepository.findByName("STUDENT")
                .orElseThrow(() -> new RuntimeException("Chưa có role STUDENT trong DB"));

        student.setPassword(passwordEncoder.encode(student.getPassword()));
        student.getRoles().add(roleStudent);

        Student saved = studentRepository.save(student);

        System.out.println("Luu thanh cong, ID = " + saved.getStudentId());

        redirectAttributes.addFlashAttribute("success", "Đăng ký thành công, hãy đăng nhập");
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }
}