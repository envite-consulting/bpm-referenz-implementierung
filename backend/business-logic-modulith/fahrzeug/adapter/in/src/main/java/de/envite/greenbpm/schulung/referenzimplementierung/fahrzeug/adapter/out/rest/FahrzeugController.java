package de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.adapter.out.rest;

import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.fahrzeug.Fahrzeug;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.fahrzeug.FahrzeugId;
import de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.usecase.in.FahrzeugAbfrage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("fahrzeug")
@RequiredArgsConstructor
class FahrzeugController {

    private final FahrzeugAbfrage fahrzeugAbfrage;
    private final FahrzeugRestMapper fahrzeugMapper;

    @GetMapping("/{fahrzeugId}")
    public ResponseEntity<FahrzeugResource> anzeigen(@PathVariable String fahrzeugId) {
        Fahrzeug fahrzeug = fahrzeugAbfrage.abfragen(new FahrzeugId(fahrzeugId));
        return ResponseEntity.ok(fahrzeugMapper.toResource(fahrzeug));
    }
}
