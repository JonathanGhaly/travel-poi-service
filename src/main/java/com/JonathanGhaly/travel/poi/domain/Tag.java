package com.JonathanGhaly.travel.poi.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tags")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String name;

    @Column(name="created_at",updatable = false)
    private OffsetDateTime createdAt;

    @ManyToMany(mappedBy = "tags")
    private Set<Poi> pois = new HashSet<>();
}
