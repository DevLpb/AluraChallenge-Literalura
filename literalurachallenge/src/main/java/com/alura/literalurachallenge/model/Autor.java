package com.alura.literalurachallenge.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(unique = true)
    private String nombre;
    private Integer fechaDeNacimiento;
    private Integer fechaDeMuerte;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL)
    private List<Libro> libros;

    public Autor() {} //Constructor predeterminado

    public Autor(DatosAutor datosAutor) {
        this.nombre = datosAutor.nombre();
        this.fechaDeNacimiento = Integer.valueOf(datosAutor.fechaDeNacimiento());
        this.fechaDeMuerte = Integer.valueOf(datosAutor.fechaDeMuerte());
    }

    //Getters & Setters

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(Integer fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public Integer getFechaDeMuerte() {
        return fechaDeMuerte;
    }

    public void setFechaDeMuerte(Integer fechaDeMuerte) {
        this.fechaDeMuerte = fechaDeMuerte;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }

    public Autor obtenerAutor(DatosLibro datosLibro) {
        DatosAutor datosAutor = datosLibro.autor().get(0);
        return new Autor(datosAutor);
    }

    @Override
    public String toString() {
        return "Nombre: " + nombre + "\'" +
                ", Año de naciemiento: " + fechaDeNacimiento +
                ", Año de muerte: " + fechaDeMuerte;
    }
}
