package de.envite.greenbpm.schulung.referenzimplementierung.bestellung.adapter.out.db;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("BESTELLUNG")
@Data
class BestellungEntity implements Persistable<String> {
  @Id
  private String id;
  private String fahrzeugreferenz;
  private String antragstellerreferenz;
  private LocalDateTime bestelldatum;
  private String status;

    @Override
    public boolean isNew() {
        // Diese Implementierung unterstützt aktuell kein Update einer Entität.
        // Denn alle `save` aufrufe werden als insert angesehen.
        return true;
    }
}
