package com.example.demo.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Immatrikulation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "kurs_id")
    private Kurs kurs;

    public Immatrikulation(@NonNull Student student, @NonNull Kurs kurs) {
        this.student = student;
        this.kurs = kurs;
    }

}
