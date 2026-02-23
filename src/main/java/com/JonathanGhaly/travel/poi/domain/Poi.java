package com.JonathanGhaly.travel.poi.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name="pois")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Poi {

    @Id
    @Column(name = "uuid", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private double latitude;

    @Column(nullable = false)
    private double longitude;

    // 2. UPDATED Mapping for JSONB
    // We use @JdbcTypeCode to tell Hibernate this is a JSON column
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "opening_hours", columnDefinition = "jsonb", nullable = false)
    private Map<String, Object> openingHours;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "poi_tags",
            joinColumns = @JoinColumn(name = "poi_uuid"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();

    @Column(name = "created_at", updatable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        if (id == null) id = UUID.randomUUID();
        createdAt = OffsetDateTime.now();
        updatedAt = OffsetDateTime.now();
    }

    @PreUpdate
    protected void onUpdate()  {
        updatedAt = OffsetDateTime.now();
    }
}