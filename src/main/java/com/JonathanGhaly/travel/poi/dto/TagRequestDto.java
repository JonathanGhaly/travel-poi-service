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
public class TagRequestDto {

    @NotBlank(message = "Name is required")
    @Size(max = 255)
    private String name;

}
