package com.example.demo.mappers;

import com.example.demo.dto.StudentDtoReq;
import com.example.demo.dto.StudentDtoRes;
import com.example.demo.models.Immatrikulation;
import com.example.demo.models.Note;
import com.example.demo.models.Student;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;

//(componentModel = "spring", builder = @Builder(disableBuilder = true))
@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface StudentMapper {
    //Alternative Methode
//    @Mapping(target = "kursIds",
//            expression = "java(student.getImmatrikulationen() == null ? " +
//                    "new java.util.ArrayList<>() : " +
//                    "student.getImmatrikulationen().stream()" +
//                    ".map(i -> i.getKurs().getId()).toList())")
    @Mapping(target = "kursIds", expression = "java(immatrikulationenToKursIds(student.getImmatrikulationen()))")
    @Mapping(target = "qtyNoten", expression = "java(notenToQtyNoten(student.getNoten()))")
    StudentDtoRes toDto(Student student);
    Student toEntity(StudentDtoReq studentDtoReq);

    default List<Long> immatrikulationenToKursIds(List<Immatrikulation> immatrikulationen) {
        if (immatrikulationen == null) return new ArrayList<>();
        return immatrikulationen.stream()
                .map(i -> i.getKurs().getId())
                .toList();
    }
    default Long notenToQtyNoten(List<Note> noten) {
        if (noten == null) return 0L;
        return (long) noten.size();
    }
}