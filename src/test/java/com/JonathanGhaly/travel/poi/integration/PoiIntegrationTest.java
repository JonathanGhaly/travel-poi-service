package com.JonathanGhaly.travel.poi.integration;

import com.JonathanGhaly.travel.poi.domain.Poi;
import com.JonathanGhaly.travel.poi.repository.PoiRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
@SpringBootTest
@ActiveProfiles("test")
@Testcontainers
class PoiIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("travel_poi")
            .withUsername("postgres")
            .withPassword("postgres");

    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private PoiRepository repository;

    @Test
    void shouldSavePoi() {
        Poi poi = Poi.builder()
                .name("Integration Test")
                .category("landmark")
                .latitude(10.0)
                .longitude(20.0)
                .openingHours(Map.of("mon", "9-5"))
                .build();

        Poi saved = repository.save(poi);
        assertNotNull(saved.getId());
    }
}
