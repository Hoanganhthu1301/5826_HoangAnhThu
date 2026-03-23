package com.example.__HoangAnhThu.repository;

import com.example.__HoangAnhThu.entity.Course;
import com.example.__HoangAnhThu.entity.Enrollment;
import com.example.__HoangAnhThu.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    boolean existsByStudentAndCourse(Student student, Course course);
    List<Enrollment> findByStudent(Student student);
}