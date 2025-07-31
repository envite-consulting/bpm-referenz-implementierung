package de.envite.greenbpm.schulung.referenzimplementierung.adapter.out.rest;

import de.envite.greenbpm.schulung.referenzimplementierung.adapter.out.rest.dto.BestellungDto;
import de.envite.greenbpm.schulung.referenzimplementierung.domain.model.Bestellung;
import de.envite.greenbpm.schulung.referenzimplementierung.usecase.in.Bestellungsverwaltung;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("bestellung/")
public class BestellungController {

    private final Bestellungsverwaltung bestellungsverwaltung;
    private final BestellungRestMapper bestellungMapper;


    @PostMapping("/")
    public ResponseEntity<BestellungDto> erfassen(@RequestBody BestellungDto bestellungDto) {

        Bestellung bestellung = bestellungsverwaltung.erfassen(bestellungMapper.toDomain(bestellungDto));

        return ResponseEntity.ok(bestellungMapper.toDto(bestellung));
    }

    @GetMapping("/{bestellungId}")
    public ResponseEntity<BestellungDto> anzeigen(@PathVariable UUID bestellungId) {

        Bestellung bestellung = bestellungsverwaltung.anzeigen(bestellungId);

        return ResponseEntity.ok(bestellungMapper.toDto(bestellung));
    }
}
