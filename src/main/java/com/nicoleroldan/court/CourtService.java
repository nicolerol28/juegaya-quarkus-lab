package com.nicoleroldan.court;

import com.nicoleroldan.court.dto.CourtRequest;
import com.nicoleroldan.court.dto.CourtResponse;
import com.nicoleroldan.shared.CourtNotFoundException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class CourtService {

    private final CourtRepository courtRepository;
    private final CourtMapper courtMapper;

    @Inject
    public CourtService(CourtRepository courtRepository, CourtMapper courtMapper) {
        this.courtRepository = courtRepository;
        this.courtMapper = courtMapper;
    }

    public List<CourtResponse> listAll() {
        return courtRepository.listAll().stream()
                .map(courtMapper::toResponse)
                .toList();
    }

    public CourtResponse findById(Long id) {
        Court court = courtRepository.findById(id);
        if (court == null) {
            throw new CourtNotFoundException(id);
        }
        return courtMapper.toResponse(court);
    }

    @Transactional
    public CourtResponse create(CourtRequest request) {
        Court court = courtMapper.toEntity(request);
        courtRepository.persist(court);
        return courtMapper.toResponse(court);
    }

    @Transactional
    public CourtResponse update(Long id, CourtRequest request) {
        Court court = courtRepository.findById(id);
        if (court == null) {
            throw new CourtNotFoundException(id);
        }
        court.setName(request.name());
        court.setSport(request.sport());
        court.setAvailable(request.available());
        return courtMapper.toResponse(court);
    }

    @Transactional
    public void delete(Long id) {
        boolean deleted = courtRepository.deleteById(id);
        if (!deleted) {
            throw new CourtNotFoundException(id);
        }
    }
}