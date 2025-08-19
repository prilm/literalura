package com.aluracursos.literalura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table (name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(unique = true)
    private String nombre;

    @Column()
    private Integer anioNacimiento;

    @Column()
    private Integer anioFallecimiento;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> libros = new ArrayList<>();

    public Autor(){}

    public Autor(MapeoJsonAutor datosAutor){
        this.nombre = datosAutor.nombre();
        this.anioNacimiento = datosAutor.anioNacimiento();
        this.anioFallecimiento = datosAutor.anioFallecimiento();
    }

    @Override
    public String toString() {
        return  "nombre = " + nombre + '\n' +
                "año de nacimiento = " + anioNacimiento + '\n' +
                "año de fallecimiento = " + anioFallecimiento + '\n' +
                "libros = " + getListaDeLibrosString() + '\n';
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public String getListaDeLibrosString() {
        return libros
                .stream()
                .map(libro -> libro.getTitulo())
                .collect(Collectors.joining(" | "));
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getAnioNacimiento() {
        return anioNacimiento;
    }

    public void setAnioNacimiento(Integer anioNacimiento) {
        this.anioNacimiento = anioNacimiento;
    }

    public Integer getAnioFallecimiento() {
        return anioFallecimiento;
    }

    public void setAnioFallecimiento(Integer anioFallecimiento) {
        this.anioFallecimiento = anioFallecimiento;
    }
}
