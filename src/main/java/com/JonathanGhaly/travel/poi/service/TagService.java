package com.JonathanGhaly.travel.poi.service;

import com.JonathanGhaly.travel.poi.domain.Tag;

import java.util.List;

public interface TagService {
    Tag create(String name);
    List<Tag> findAll();
}
