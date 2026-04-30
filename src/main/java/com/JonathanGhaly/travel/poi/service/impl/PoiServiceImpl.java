package com.JonathanGhaly.travel.poi.service.impl;

import com.JonathanGhaly.travel.poi.domain.Poi;
import com.JonathanGhaly.travel.poi.domain.Tag;
import com.JonathanGhaly.travel.poi.dto.PoiEventDto;
import com.JonathanGhaly.travel.poi.dto.PoiRequestDto;
import com.JonathanGhaly.travel.poi.dto.PoiResponseDto;
import com.JonathanGhaly.travel.poi.exception.ResourceNotFoundException;
import com.JonathanGhaly.travel.poi.kafka.publisher.PoiEventPublisher;
import com.JonathanGhaly.travel.poi.mapper.PoiMapper;
import com.JonathanGhaly.travel.poi.repository.PoiRepository;
import com.JonathanGhaly.travel.poi.repository.TagRepository;
import com.JonathanGhaly.travel.poi.service.PoiService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PoiServiceImpl implements PoiService {

    private final PoiRepository repository;
    private final PoiMapper mapper;
    private final TagRepository tagRepository;
    private final PoiEventPublisher eventPublisher; // Injected Publisher

    private Set<Tag> resolveTags(Set<String> tagNames) {
        return tagNames.stream()
                .map(name -> tagRepository.findByName(name)
                        .orElseGet(() ->
                                tagRepository.save(
                                        Tag.builder()
                                                .name(name)
                                                .build()
                                )
                        )
                )
                .collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public PoiResponseDto create(PoiRequestDto dto) {

        Poi entity = mapper.toEntity(dto);

        if (dto.getTags() != null && !dto.getTags().isEmpty()) {
            entity.setTags(resolveTags(dto.getTags()));
        }

        Poi saved = repository.save(entity);
        PoiResponseDto response = mapper.toDto(saved);

        // PUBLISH EVENT
        eventPublisher.publish(mapToEventDto(response), "CREATED");

        return response;
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
    @Transactional
    public PoiResponseDto update(UUID id, PoiRequestDto dto) {

        Poi existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("POI not found"));

        existing.setName(dto.getName());
        existing.setDescription(dto.getDescription());
        existing.setCategory(dto.getCategory());
        existing.setLatitude(dto.getLatitude());
        existing.setLongitude(dto.getLongitude());
        existing.setOpeningHours(dto.getOpeningHours());

        if (dto.getTags() != null) {
            existing.setTags(resolveTags(dto.getTags()));
        }

        Poi saved = repository.save(existing);
        PoiResponseDto response = mapper.toDto(saved);

        // PUBLISH EVENT
        eventPublisher.publish(mapToEventDto(response), "UPDATED");

        return response;
    }

    @Override
    @CacheEvict(value = "pois", key = "#id")
    @Transactional
    public PoiResponseDto patch(UUID id, PoiRequestDto dto) {

        Poi existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("POI not found"));

        if (dto.getName() != null) existing.setName(dto.getName());
        if (dto.getDescription() != null) existing.setDescription(dto.getDescription());
        if (dto.getCategory() != null) existing.setCategory(dto.getCategory());
        if (dto.getLatitude() != null) existing.setLatitude(dto.getLatitude());
        if (dto.getLongitude() != null) existing.setLongitude(dto.getLongitude());
        if (dto.getOpeningHours() != null) existing.setOpeningHours(dto.getOpeningHours());

        if (dto.getTags() != null) {
            existing.setTags(resolveTags(dto.getTags()));
        }

        Poi saved = repository.save(existing);
        PoiResponseDto response = mapper.toDto(saved);

        // PUBLISH EVENT
        eventPublisher.publish(mapToEventDto(response), "UPDATED");

        return response;
    }

    @Override
    @Transactional
    @CacheEvict(value = "pois", key = "#poiUuid") // Added missing cache eviction
    public void addTagToPoi(UUID poiUuid, String tagName) {
        Poi poi = repository.findById(poiUuid)
                .orElseThrow(() -> new ResourceNotFoundException("POI not found"));
        Tag tag = tagRepository.findByName(tagName)
                .orElseGet(() ->
                        tagRepository.save(
                                Tag.builder()
                                        .name(tagName)
                                        .build()
                        )
                );
        poi.getTags().add(tag);
        Poi saved = repository.save(poi);

        // PUBLISH EVENT (Modifying tags is an update to the POI)
        eventPublisher.publish(mapToEventDto(mapper.toDto(saved)), "UPDATED");
    }

    @Override
    public List<PoiResponseDto> getByTagName(String tagName) {
        return repository.findByTagName(tagName)
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    @CacheEvict(value = "pois", key = "#id")
    @Transactional
    public void delete(UUID id) {
        Poi existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("POI not found"));

        // Map to DTO before deleting so we have the payload to send in the event
        PoiResponseDto response = mapper.toDto(existing);

        repository.delete(existing);

        // PUBLISH EVENT
        eventPublisher.publish(mapToEventDto(response), "DELETED");
    }

    // Helper method to map Response DTO to Event DTO
    private PoiEventDto mapToEventDto(PoiResponseDto dto) {
        return new PoiEventDto(
                dto.getId(),
                dto.getName(),
                dto.getDescription(),
                dto.getCategory(),
                dto.getLatitude(),
                dto.getLongitude(),
                dto.getOpeningHours(),
                dto.getTags(),
                dto.getCreatedAt(),
                dto.getUpdatedAt()
        );
    }
}