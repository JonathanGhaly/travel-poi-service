package com.JonathanGhaly.travel.poi.controller;

import com.JonathanGhaly.travel.poi.domain.Tag;
import com.JonathanGhaly.travel.poi.dto.TagRequestDto;
import com.JonathanGhaly.travel.poi.dto.TagResponseDto;
import com.JonathanGhaly.travel.poi.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @PostMapping
    public ResponseEntity<TagResponseDto> createTag(@RequestBody TagRequestDto request) {
        Tag tag = tagService.create(request.getName());
        return ResponseEntity.ok(new TagResponseDto(tag.getId(), tag.getName()));
    }

    @GetMapping
    public List<TagResponseDto> getAllTags() {
        return tagService.findAll()
                .stream()
                .map(tag -> new TagResponseDto(tag.getId(), tag.getName()))
                .toList();
    }
}