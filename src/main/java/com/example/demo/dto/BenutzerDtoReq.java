package com.example.demo.dto;

import com.example.demo.models.Role;
import lombok.Data;

@Data
public class BenutzerDtoReq {

    private String loginName;

    private String password;

    private Role role;

}
