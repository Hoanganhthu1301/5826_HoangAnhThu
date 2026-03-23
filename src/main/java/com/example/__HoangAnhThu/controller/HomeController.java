package com.example.__HoangAnhThu.controller;

import com.example.__HoangAnhThu.entity.Course;
import com.example.__HoangAnhThu.repository.CourseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    private final CourseRepository courseRepository;

    public HomeController(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @GetMapping({"/", "/home", "/courses"})
    public String home(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0") int page,
            Model model
    ) {
        Page<Course> coursePage;

        if (keyword != null && !keyword.trim().isEmpty()) {
            coursePage = courseRepository.findByNameContainingIgnoreCase(keyword, PageRequest.of(page, 5));
        } else {
            coursePage = courseRepository.findAll(PageRequest.of(page, 5));
        }

        model.addAttribute("coursePage", coursePage);
        model.addAttribute("courses", coursePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);

        return "home";
    }
}