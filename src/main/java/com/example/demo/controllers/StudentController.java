package com.example.demo.controllers;

import com.example.demo.dto.StudentDtoReq;
import com.example.demo.dto.StudentDtoRes;
import com.example.demo.services.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    StudentService studentService;

    public StudentController(StudentService studentService){
        this.studentService = studentService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDtoRes> getStudentById(@PathVariable Long id){

        return studentService.getStudentById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @GetMapping("/")
    public ResponseEntity<List<StudentDtoRes>> getAllStudents(){

        return ResponseEntity.ok(studentService.getAllStudents());

    }

    @PostMapping("/")
    public ResponseEntity<StudentDtoRes> createStudent(@RequestBody StudentDtoReq studentReq) {
        StudentDtoRes createdStudent = studentService.createStudent(studentReq);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdStudent.getId())
                .toUri();
        return ResponseEntity.created(location).body(createdStudent);
    }

}
