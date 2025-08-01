package de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.adapter.out.rest.config;

import de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.adapter.out.rest.FahrzeugResource;
import de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.usecase.exception.FahrzeugNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("error")
class RestAdviceSampleController {

    @GetMapping("/fahrzeugNotFoundException")
    public ResponseEntity<FahrzeugResource> throwFahrzeugNotFoundException() {
        throw new FahrzeugNotFoundException("Das ist ein Test");
    }
}
