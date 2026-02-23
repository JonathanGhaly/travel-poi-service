package com.JonathanGhaly.travel.poi.mapper;

import com.JonathanGhaly.travel.poi.domain.Poi;
import com.JonathanGhaly.travel.poi.domain.Tag;
import com.JonathanGhaly.travel.poi.dto.PoiRequestDto;
import com.JonathanGhaly.travel.poi.dto.PoiResponseDto;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.stream.Collectors;

@Component
public class PoiMapper {

    public Poi toEntity(PoiRequestDto dto) {
        return Poi.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .category(dto.getCategory())
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .openingHours(dto.getOpeningHours())
                .build();
    }

    public PoiResponseDto toDto(Poi entity) {
        return PoiResponseDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .category(entity.getCategory())
                .latitude(entity.getLatitude())
                .longitude(entity.getLongitude())
                .openingHours(entity.getOpeningHours())
                .tags(
                        entity.getTags() == null
                                ? new HashSet<>()
                                : entity.getTags()
                                .stream()
                                .map(Tag::getName)
                                .collect(Collectors.toSet())
                )
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}