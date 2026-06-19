package com.nicoleroldan.court.dto;

public record CourtResponse(
        Long id,
        String name,
        String sport,
        boolean available
) {}