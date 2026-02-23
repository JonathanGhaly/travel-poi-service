package com.JonathanGhaly.travel.poi.service.impl;

import com.JonathanGhaly.travel.poi.domain.Tag;
import com.JonathanGhaly.travel.poi.repository.TagRepository;
import com.JonathanGhaly.travel.poi.service.TagService;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class TagImpl implements TagService {
    private TagRepository tagRepository;
    @Override
    public Tag create(String name) {
        if (tagRepository.existsByName(name)) {
            throw new IllegalArgumentException("Tag with name " + name + " already exists");
        }
        return tagRepository.save(
         Tag.builder()
                 .name(name)
                 .createdAt(OffsetDateTime.now())
                 .build()
        );
    }

    @Override
    public List<Tag> findAll() {
        return tagRepository.findAll();
    }
}
