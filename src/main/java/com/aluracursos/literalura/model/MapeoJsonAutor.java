package com.aluracursos.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public record MapeoJsonAutor(
        @JsonAlias("name") String nombre,
        @JsonAlias("birth_year") Integer anioNacimiento,
        @JsonAlias("death_year") Integer anioFallecimiento) {
}
