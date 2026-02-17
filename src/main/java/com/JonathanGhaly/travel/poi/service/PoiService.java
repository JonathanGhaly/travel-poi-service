package com.JonathanGhaly.travel.poi.service;


import com.JonathanGhaly.travel.poi.dto.PoiRequestDto;
import com.JonathanGhaly.travel.poi.dto.PoiResponseDto;

import java.util.List;
import java.util.UUID;

public interface PoiService {

    PoiResponseDto create(PoiRequestDto dto);

    PoiResponseDto getById(UUID id);

    List<PoiResponseDto> getAll();

    List<PoiResponseDto> getByCategory(String category);

    PoiResponseDto update(UUID id, PoiRequestDto dto);

    PoiResponseDto patch(UUID id, PoiRequestDto dto);

    void delete(UUID id);
}