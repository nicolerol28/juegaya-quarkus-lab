package com.nicoleroldan.court.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CourtRequest(
        @NotBlank(message = "El nombre es obligatorio")
        String name,

        @NotBlank(message = "El deporte es obligatorio")
        @Pattern(regexp = "futbol5|tenis|padel", message = "Deporte no permitido")
        String sport,

        boolean available
) {}