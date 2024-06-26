package com.alura.literalurachallenge.model;

import jakarta.persistence.*;

@Entity
@Table(name = "libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(unique = true)
    private String titulo;

    @ManyToOne()
    private Autor autor;

    private String nombreAutor;
    private String idiomas;
    private Double numeroDescargas;

    public Libro() {} //Constructor predeterminado

    public Libro(DatosLibro datosLibro) {
        this.titulo = datosLibro.titulo();
        this.nombreAutor = obtenerAutor(datosLibro).getNombre();
        this.idiomas = obtenerIdioma(datosLibro);
        this.numeroDescargas = datosLibro.numeroDescargas();
    }

    //Getters & Setters
    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getNombreAutor() {
        return nombreAutor;
    }

    public void setNombreAutor(String nombreAutor) {
        this.nombreAutor = nombreAutor;
    }

    public String getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(String idiomas) {
        this.idiomas = idiomas;
    }

    public Double getNumeroDescargas() {
        return numeroDescargas;
    }

    public void setNumeroDescargas(Double numeroDescargas) {
        this.numeroDescargas = numeroDescargas;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Autor obtenerAutor(DatosLibro datosLibro) {
        DatosAutor datosAutor = datosLibro.autor().get(0);
        return new Autor(datosAutor);
    }

    public String obtenerIdioma(DatosLibro datosLibro) {
        String idioma = datosLibro.idioma().toString();
        return idioma;
    }

    @Override
    public String toString() {
        return "Titulo: " + titulo + "\'"
                + ", Autor:" + nombreAutor +
                ", Idiomas:" + idiomas+
                " , Numero de descargas: " + numeroDescargas;
    }


}
