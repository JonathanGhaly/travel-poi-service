package com.JonathanGhaly.travel.poi.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Map;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PoiRequestDto {
    @NotBlank(message = "Name is required")
    @Size(max = 255)
    private String name;
    @Size(max = 2000)
    private String description;
    @NotBlank(message = "Category is required")
    @Size(max = 50)
    private String category;
    @NotNull(message = "Latitude is required")
    @DecimalMin(value = "-90.0", message = "Latitude must be >= -90")
    @DecimalMax(value = "90.0", message = "Latitude must be <= 90")
    private Double latitude;
    @NotNull(message = "Longitude is required")
    @DecimalMin(value = "-180.0", message = "Longitude must be >= -180")
    @DecimalMax(value = "180.0", message = "Longitude must be <= 180")
    private Double longitude;
    @NotBlank(message = "Opening hours JSON is required")
    private Map<String, Object> openingHours;


    private Set<
            @NotBlank(message = "Tag cannot be blank")
            @Size(max = 50)
            String> tags;
}
