package com.nicoleroldan.court;

import com.nicoleroldan.court.dto.CourtRequest;
import com.nicoleroldan.court.dto.CourtResponse;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CourtMapper {

    public Court toEntity(CourtRequest request) {
        Court court = new Court();
        court.setName(request.name());
        court.setSport(request.sport());
        court.setAvailable(request.available());
        return court;
    }

    public CourtResponse toResponse(Court court) {
        return new CourtResponse(court.getId(), court.getName(), court.getSport(), court.isAvailable());
    }
}
