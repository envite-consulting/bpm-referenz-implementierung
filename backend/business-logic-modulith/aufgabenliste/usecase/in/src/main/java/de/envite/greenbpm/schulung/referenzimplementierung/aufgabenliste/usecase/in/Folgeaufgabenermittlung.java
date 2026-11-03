package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.in;

import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.model.aufgabe.FolgeaufgabeVorschlag;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface Folgeaufgabenermittlung {

  CompletableFuture<FolgeaufgabeVorschlag> ermitteln(
      String vorgangId, Optional<String> letztesArbeitspaket, Optional<Long> timeoutInMillis);
}
