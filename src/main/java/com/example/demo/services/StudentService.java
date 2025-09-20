package com.example.demo.services;

import aj.org.objectweb.asm.commons.Remapper;
import com.example.demo.dto.StudentDtoReq;
import com.example.demo.dto.StudentDtoRes;
import com.example.demo.exception.DuplicateEmailException;
import com.example.demo.exception.InvalidDataException;
import com.example.demo.mappers.StudentMapper;
import com.example.demo.models.Student;
import com.example.demo.repositories.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    public StudentService(StudentRepository studentRepository, StudentMapper studentMapper){
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
    }

    public Optional<StudentDtoRes> getStudentById(Long id){

        return studentRepository.findById(id)
                .map(studentMapper::toDto);
    }

    public List<StudentDtoRes> getAllStudents() {

        return studentRepository.findAll().stream().map(studentMapper::toDto).toList();
    }

    public StudentDtoRes createStudent(StudentDtoReq studentReq) {

        //throw new InvalidDataException("Kann throw");
        Student student = studentMapper.toEntity(studentReq);
        Student savedStudent = studentRepository.save(student);
        return studentMapper.toDto(savedStudent);

    }

    public StudentDtoRes updateStudent(Long id, @Valid StudentDtoReq studentDtoReq) {

        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));

        student.setName(studentDtoReq.getName());

        Student saved = studentRepository.save(student);
        return studentMapper.toDto(saved);

    }

    public void deleteStudent(Long id) {

        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id " + id));
        studentRepository.delete(student);

    }
}
