package com.example.demo.mappers;

import com.example.demo.dto.StudentDtoReq;
import com.example.demo.dto.StudentDtoRes;
import com.example.demo.models.Student;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
//(componentModel = "spring", builder = @Builder(disableBuilder = true))
@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface StudentMapper {
    StudentDtoRes toDto(Student student);
    Student toEntity(StudentDtoReq studentDtoReq);
}