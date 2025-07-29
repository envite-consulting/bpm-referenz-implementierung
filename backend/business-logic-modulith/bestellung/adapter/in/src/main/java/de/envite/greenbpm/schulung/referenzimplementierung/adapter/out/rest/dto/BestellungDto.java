package de.envite.greenbpm.schulung.referenzimplementierung.adapter.out.rest.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record BestellungDto(UUID antragstellerId, ProduktDto produkt, LocalDateTime bestelldatum, String status) {
}
