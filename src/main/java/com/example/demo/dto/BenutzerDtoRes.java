package com.example.demo.dto;

import com.example.demo.models.Role;
import lombok.Data;

@Data
public class BenutzerDtoRes {

    private Long id;

    private String loginName;

    private Role role;

}
