package com.JonathanGhaly.travel.poi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PoiEventDto {
    UUID poiId;
    private String name;
    private String description;
    private String category;
    private Double latitude;
    private Double longitude;
    private Map<String, Object> openingHours;
    private Set<String> tags;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
