package com.example.demo.repositories;

import com.example.demo.dto.StudentDtoRes;
import com.example.demo.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<StudentDtoRes> getStudentById(Long id);
}
