package com.example.demo.dto;

import lombok.*;

import java.util.List;

@Data
public class StudentDtoRes {

    private Long id;
    private String name;
    private List<Long> kursIds;
    private Long qtyNoten;
}
