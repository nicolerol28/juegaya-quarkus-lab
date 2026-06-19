package com.nicoleroldan.shared;

public class CourtNotFoundException extends RuntimeException {

    public CourtNotFoundException(Long id) {
        super("Cancha no encontrada: " + id);
    }
}
