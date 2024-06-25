package com.alura.literalurachallenge.repository;

import com.alura.literalurachallenge.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibroRepository extends JpaRepository<Libro, Long> {
}
