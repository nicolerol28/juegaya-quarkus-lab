package com.nicoleroldan.court;

import com.nicoleroldan.court.dto.CourtRequest;
import com.nicoleroldan.court.dto.CourtResponse;
import com.nicoleroldan.shared.CourtNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CourtServiceTest {

    @Mock
    CourtRepository courtRepository;

    @Mock
    CourtMapper courtMapper;

    CourtService courtService;

    @BeforeEach
    void setUp() {
        courtService = new CourtService(courtRepository, courtMapper);
    }

    @Test
    void findById_existingCourt_returnsResponse() {
        Court court = new Court();
        CourtResponse expected = new CourtResponse(1L, "Central", "tenis", true);
        when(courtRepository.findById(1L)).thenReturn(court);
        when(courtMapper.toResponse(court)).thenReturn(expected);

        CourtResponse result = courtService.findById(1L);

        assertEquals(expected, result);
        verify(courtRepository).findById(1L);
        verify(courtMapper).toResponse(court);
    }

    @Test
    void findById_notFound_throwsCourtNotFoundException() {
        when(courtRepository.findById(99L)).thenReturn(null);

        CourtNotFoundException ex = assertThrows(
                CourtNotFoundException.class,
                () -> courtService.findById(99L));
        assertTrue(ex.getMessage().contains("99"));
    }

    @Test
    void listAll_returnsMappedList() {
        Court court = new Court();
        CourtResponse expected = new CourtResponse(1L, "Central", "tenis", true);
        when(courtRepository.listAll()).thenReturn(List.of(court));
        when(courtMapper.toResponse(court)).thenReturn(expected);

        List<CourtResponse> result = courtService.listAll();

        assertEquals(List.of(expected), result);
    }

    @Test
    void create_persistsAndReturnsResponse() {
        CourtRequest request = new CourtRequest("Central", "tenis", true);
        Court court = new Court();
        CourtResponse expected = new CourtResponse(1L, "Central", "tenis", true);
        when(courtMapper.toEntity(request)).thenReturn(court);
        when(courtMapper.toResponse(court)).thenReturn(expected);

        CourtResponse result = courtService.create(request);

        assertEquals(expected, result);
        verify(courtRepository).persist(court);
    }

    @Test
    void update_existingCourt_appliesSettersAndReturnsResponse() {
        Court court = new Court();
        CourtRequest request = new CourtRequest("Renovada", "padel", false);
        CourtResponse expected = new CourtResponse(1L, "Renovada", "padel", false);
        when(courtRepository.findById(1L)).thenReturn(court);
        when(courtMapper.toResponse(court)).thenReturn(expected);

        CourtResponse result = courtService.update(1L, request);

        assertEquals(expected, result);
        assertEquals("Renovada", court.getName());
        assertEquals("padel", court.getSport());
        assertFalse(court.isAvailable());
        verify(courtMapper).toResponse(court);
    }

    @Test
    void update_notFound_throwsCourtNotFoundException() {
        when(courtRepository.findById(99L)).thenReturn(null);

        CourtNotFoundException ex = assertThrows(
                CourtNotFoundException.class,
                () -> courtService.update(99L, new CourtRequest("x", "tenis", true)));
        assertTrue(ex.getMessage().contains("99"));
    }

    @Test
    void delete_existingCourt_callsRepository() {
        when(courtRepository.deleteById(1L)).thenReturn(true);

        courtService.delete(1L);

        verify(courtRepository).deleteById(1L);
    }

    @Test
    void delete_notFound_throwsCourtNotFoundException() {
        when(courtRepository.deleteById(99L)).thenReturn(false);

        CourtNotFoundException ex = assertThrows(
                CourtNotFoundException.class,
                () -> courtService.delete(99L));
        assertTrue(ex.getMessage().contains("99"));
    }
}
