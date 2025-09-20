package com.example.demo.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Kurs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "kurs", cascade = CascadeType.ALL, orphanRemoval = true )
    private List<Immatrikulation> immatrikulationen = new ArrayList<>();

    @OneToMany(mappedBy = "kurs", cascade = CascadeType.ALL, orphanRemoval = true )
    private List<Note> noten = new ArrayList<>();

    public Kurs(String name) {
        this.name = name;
    }
}
