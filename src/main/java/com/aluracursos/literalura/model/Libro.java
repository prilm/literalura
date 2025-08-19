package com.aluracursos.literalura.model;

import jakarta.persistence.*;

@Entity
@Table (name = "libros")
public class Libro {
    @Id
    private Long Id;

    @ManyToOne
    private Autor autor;

    @Column()
    private String titulo;

    @Column()
    private String idioma;

    @Column()
    private Integer numeroDescargas;

    public Libro(){}

    public Libro(MapeoJsonLibro datosLibro){
        this.Id = datosLibro.id();
        this.titulo = datosLibro.titulo();
        this.idioma = datosLibro.idiomas().get(0);
        this.numeroDescargas = datosLibro.numeroDescargas();
    }

    @Override
    public String toString() {
        return  "titulo = " + titulo + '\n' +
                "autor = " + autor.getNombre() + '\n' +
                "idioma = " + idioma + '\n' +
                "numeroDescargas = " + numeroDescargas + '\n';
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Integer getNumeroDescargas() {
        return numeroDescargas;
    }

    public void setNumeroDescargas(Integer numeroDescargas) {
        this.numeroDescargas = numeroDescargas;
    }
}
