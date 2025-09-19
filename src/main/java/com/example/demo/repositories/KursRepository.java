package com.example.demo.repositories;

import com.example.demo.models.Kurs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KursRepository extends JpaRepository<Kurs, Long> {

}
