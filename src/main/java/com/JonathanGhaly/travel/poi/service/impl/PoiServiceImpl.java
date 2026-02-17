package com.JonathanGhaly.travel.poi.service.impl;

import com.JonathanGhaly.travel.poi.domain.Poi;
import com.JonathanGhaly.travel.poi.dto.PoiRequestDto;
import com.JonathanGhaly.travel.poi.dto.PoiResponseDto;
import com.JonathanGhaly.travel.poi.exception.ResourceNotFoundException;
import com.JonathanGhaly.travel.poi.mapper.PoiMapper;
import com.JonathanGhaly.travel.poi.repository.PoiRepository;
import com.JonathanGhaly.travel.poi.service.PoiService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PoiServiceImpl implements PoiService {

    private final PoiRepository repository;
    private final PoiMapper mapper;

    @Override
    public PoiResponseDto create(PoiRequestDto dto) {
        Poi entity = mapper.toEntity(dto);
        return mapper.toDto(repository.save(entity));
    }

    @Override
    @Cacheable(value = "pois", key = "#id")
    public PoiResponseDto getById(UUID id) {
        Poi poi = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("POI not found"));
        return mapper.toDto(poi);
    }

    @Override
    public List<PoiResponseDto> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public List<PoiResponseDto> getByCategory(String category) {
        return repository.findByCategory(category)
                .stream()
                .map(mapper::toDto)
                .toList();
    }
    @Override
    @CacheEvict(value = "pois", key = "#id")
    public PoiResponseDto update(UUID id, PoiRequestDto dto) {

        Poi existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("POI not found"));

        existing.setName(dto.getName());
        existing.setDescription(dto.getDescription());
        existing.setCategory(dto.getCategory());
        existing.setLatitude(dto.getLatitude());
        existing.setLongitude(dto.getLongitude());
        existing.setOpeningHours(dto.getOpeningHours());
        existing.setTags(dto.getTags());

        return mapper.toDto(repository.save(existing));
    }
    @Override
    public PoiResponseDto patch(UUID id, PoiRequestDto dto) {

        Poi existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("POI not found"));

        if (dto.getName() != null) existing.setName(dto.getName());
        if (dto.getDescription() != null) existing.setDescription(dto.getDescription());
        if (dto.getCategory() != null) existing.setCategory(dto.getCategory());
        if (dto.getLatitude() != null) existing.setLatitude(dto.getLatitude());
        if (dto.getLongitude() != null) existing.setLongitude(dto.getLongitude());
        if (dto.getOpeningHours() != null) existing.setOpeningHours(dto.getOpeningHours());
        if (dto.getTags() != null) existing.setTags(dto.getTags());

        return mapper.toDto(repository.save(existing));
    }

    @Override
    @CacheEvict(value = "pois", key = "#id")
    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("POI not found");
        }
        repository.deleteById(id);
    }
}