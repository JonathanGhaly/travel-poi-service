package com.JonathanGhaly.travel.poi.controller;


import com.JonathanGhaly.travel.poi.dto.PoiRequestDto;
import com.JonathanGhaly.travel.poi.dto.PoiResponseDto;
import com.JonathanGhaly.travel.poi.service.PoiService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/pois")
@RequiredArgsConstructor
public class PoiController {

    private final PoiService service;

    /**
     * Create POI
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PoiResponseDto create(@Valid @RequestBody PoiRequestDto request) {
        return service.create(request);
    }

    /**
     * Get POI by ID
     */
    @GetMapping("/{id}")
    public PoiResponseDto getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    /**
     * Get all POIs
     */
    @GetMapping
    public List<PoiResponseDto> getAll(
            @RequestParam(required = false) String category
    ) {
        if (category != null) {
            return service.getByCategory(category);
        }
        return service.getAll();
    }
    @PutMapping("/{id}")
    public PoiResponseDto update(
            @PathVariable UUID id,
            @Valid @RequestBody PoiRequestDto request
    ) {
        return service.update(id, request);
    }
    @PatchMapping("/{id}")
    public PoiResponseDto patch(
            @PathVariable UUID id,
            @RequestBody PoiRequestDto request
    ) {
        return service.patch(id, request);
    }
    @PostMapping("/{poiId}/tags")
    public ResponseEntity<Void> addTagToPoi(
            @PathVariable UUID poiId,
            @RequestParam String tagName
    ) {
        service.addTagToPoi(poiId, tagName);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/by-tag/{tagName}")
    public List<PoiResponseDto> getPoisByTag(@PathVariable String tagName) {
        return service.getByTagName(tagName);
    }
    /**
     * Delete POI
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}