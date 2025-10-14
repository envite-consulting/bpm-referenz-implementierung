package de.envite.greenbpm.schulung.referenzimplementierung.uuidgenerator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.relational.core.mapping.event.BeforeConvertCallback;

import java.util.UUID;

@Configuration
public class UUIDGenerator {

    @Bean
    BeforeConvertCallback<UuidEntity> beforeConvertCallback() {
        return (entity) -> {
            if (entity.getId() == null) {
                entity.setId(UUID.randomUUID().toString());
            }
            return entity;
        };
    }
}
