package com.JonathanGhaly.travel.poi.service;

import com.JonathanGhaly.travel.poi.domain.Poi;
import com.JonathanGhaly.travel.poi.dto.PoiRequestDto;
import com.JonathanGhaly.travel.poi.dto.PoiResponseDto;
import com.JonathanGhaly.travel.poi.mapper.PoiMapper;
import com.JonathanGhaly.travel.poi.repository.PoiRepository;
import com.JonathanGhaly.travel.poi.service.impl.PoiServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PoiServiceImplTest {

    @Mock
    private PoiRepository repository;

    @Mock
    private PoiMapper mapper;

    @InjectMocks
    private PoiServiceImpl service;

    @Test
    void shouldCreatePoi() {
        PoiRequestDto request = PoiRequestDto.builder()
                .name("Test")
                .category("landmark")
                .latitude(10.0)
                .longitude(20.0)
                .openingHours(Map.of("mon", "9-5"))
                .build();

        Poi entity = new Poi();
        PoiResponseDto response = new PoiResponseDto();

        when(mapper.toEntity(request)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(response);

        PoiResponseDto result = service.create(request);

        assertNotNull(result);
        verify(repository).save(entity);
    }
}