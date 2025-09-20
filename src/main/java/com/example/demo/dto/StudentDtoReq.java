package com.example.demo.dto;

//import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
@Builder
@Data
public class StudentDtoReq {

    @NotBlank
    private String name;

}
