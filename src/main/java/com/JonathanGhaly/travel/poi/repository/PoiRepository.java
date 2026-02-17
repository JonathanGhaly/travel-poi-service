package com.JonathanGhaly.travel.poi.repository;

import com.JonathanGhaly.travel.poi.domain.Poi;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PoiRepository extends JpaRepository<Poi, UUID> {
    List<Poi> findByCategory(String category);
}
