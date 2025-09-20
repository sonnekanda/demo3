package com.example.demo.auth;

import com.example.demo.models.Role;
import lombok.Data;

@Data
public class BenutzerDtoReq {

    private String loginName;

    private String password;

    private Role role;

}
