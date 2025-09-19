package com.example.demo.config;

import com.example.demo.models.Immatrikulation;
import com.example.demo.models.Kurs;
import com.example.demo.models.Note;
import com.example.demo.models.Student;
import com.example.demo.repositories.ImmatrikulationRepository;
import com.example.demo.repositories.KursRepository;
import com.example.demo.repositories.NoteRepository;
import com.example.demo.repositories.StudentRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class Common {
    private final StudentRepository studentRepository;
    private final KursRepository kursRepository;
    private final ImmatrikulationRepository immatRepository;
    private final NoteRepository noteRepository;

    public Common(StudentRepository studentRepository, KursRepository kursRepository, ImmatrikulationRepository immatRepository, NoteRepository noteRepository) {
        this.studentRepository = studentRepository;
        this.kursRepository = kursRepository;
        this.immatRepository = immatRepository;
        this.noteRepository = noteRepository;
    }

    @Bean
    public boolean generateDaten(){
        Kurs mathe = new Kurs("Mathe");
        Kurs deutsch = new Kurs("Deutsch");
        kursRepository.save(mathe);
        kursRepository.save(deutsch);
        Student felix = new Student("Felix","");
        Student hans = new Student("Hans","");
        Student maria = new Student("Maria","");
        studentRepository.save(felix);
        studentRepository.save(hans);
        studentRepository.save(maria);

        immatRepository.save(new Immatrikulation(felix, mathe));
        immatRepository.save(new Immatrikulation(felix, deutsch));
        immatRepository.save(new Immatrikulation(hans, mathe));
        immatRepository.save(new Immatrikulation(maria, mathe));

        noteRepository.save(new Note(felix, mathe, 1));
        noteRepository.save(new Note(felix, deutsch, 2));
        noteRepository.save(new Note(hans, mathe, 3));
        noteRepository.save(new Note(maria, mathe, 4));
        noteRepository.save(new Note(felix, deutsch, 5));

        return true;
    }
}
