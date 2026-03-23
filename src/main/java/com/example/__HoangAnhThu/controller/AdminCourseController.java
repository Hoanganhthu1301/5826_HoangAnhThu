package com.example.__HoangAnhThu.controller;


import com.example.__HoangAnhThu.entity.Category;
import com.example.__HoangAnhThu.entity.Course;
import com.example.__HoangAnhThu.repository.CategoryRepository;
import com.example.__HoangAnhThu.repository.CourseRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/courses")
public class AdminCourseController {

    private final CourseRepository courseRepository;
    private final CategoryRepository categoryRepository;

    public AdminCourseController(CourseRepository courseRepository,
                                 CategoryRepository categoryRepository) {
        this.courseRepository = courseRepository;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public String listCourses(Model model) {
        List<Course> courses = courseRepository.findAll();
        model.addAttribute("courses", courses);
        return "admin/course-list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("course", new Course());
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("pageTitle", "Thêm học phần");
        return "admin/course-form";
    }

    @PostMapping("/save")
    public String saveCourse(@ModelAttribute("course") Course course,
                             RedirectAttributes redirectAttributes) {
        courseRepository.save(course);
        redirectAttributes.addFlashAttribute("success", "Lưu học phần thành công");
        return "redirect:/admin/courses";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy course"));

        model.addAttribute("course", course);
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("pageTitle", "Cập nhật học phần");
        return "admin/course-form";
    }

    @GetMapping("/delete/{id}")
    public String deleteCourse(@PathVariable Long id,
                               RedirectAttributes redirectAttributes) {
        courseRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "Xóa học phần thành công");
        return "redirect:/admin/courses";
    }
}