package com.JonathanGhaly.travel.poi.dto;

import lombok.*;

import java.time.OffsetDateTime;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PoiResponseDto {
    private UUID id;
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
