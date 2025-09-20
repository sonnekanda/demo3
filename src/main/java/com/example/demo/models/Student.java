package com.example.demo.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student extends Person {

    private String password;
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true )
    private List<Immatrikulation> immatrikulationen = new ArrayList<>();

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true )
    private List<Note> noten = new ArrayList<>();

    public Student(String name, String password) {
        super(name);
        this.password = password;
    }

}
