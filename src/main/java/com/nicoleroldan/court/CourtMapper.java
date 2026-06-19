package com.nicoleroldan.court;

import com.nicoleroldan.court.dto.CourtRequest;
import com.nicoleroldan.court.dto.CourtResponse;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CourtMapper {

    public Court toEntity(CourtRequest request) {
        Court court = new Court();
        court.name = request.name();
        court.sport = request.sport();
        court.available = request.available();
        return court;
    }

    public CourtResponse toResponse(Court court) {
        return new CourtResponse(court.id, court.name, court.sport, court.available);
    }
}