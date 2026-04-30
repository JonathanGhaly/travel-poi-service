package com.JonathanGhaly.travel.poi.kafka.publisher;

import com.JonathanGhaly.travel.poi.dto.PoiEventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PoiEventPublisher {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final String topic = "poi-events"; // Fixed topic name

    public void publish(PoiEventDto event, String status) {
        // Add status to event payload if needed, or wrap it.
        // Assuming you want to know what happened:
        log.info("Publishing POI event: {} for POI: {}", status, event.getPoiId());
        kafkaTemplate.send(topic, event.getPoiId().toString(), event);
    }
}