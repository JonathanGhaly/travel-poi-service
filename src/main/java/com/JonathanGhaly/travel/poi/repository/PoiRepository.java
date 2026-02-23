package com.JonathanGhaly.travel.poi.repository;

import com.JonathanGhaly.travel.poi.domain.Poi;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface PoiRepository extends JpaRepository<Poi, UUID> {
    List<Poi> findByCategory(String category);
    @Query("""
       SELECT p FROM Poi p
       JOIN p.tags t
       WHERE t.name = :tagName
       """)
    List<Poi> findByTagName(@Param("tagName") String tagName);
}
