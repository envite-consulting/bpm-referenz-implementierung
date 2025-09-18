package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.service;

import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.model.vorgang.Vorgang;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.model.vorgang.fachdaten.Fachdaten;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.model.vorgang.fachdaten.FachdatenAntragsteller;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.model.vorgang.fachdaten.FachdatenFahrzeug;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.model.vorgang.fachdaten.FachdatenReferenz;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.in.Vorgangabfrage;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.out.FachdatenAntragstellerQuery;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.out.FachdatenFahrzeugQuery;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.out.FachdatenReferenzQuery;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.out.VorgangQuery;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class VorgangDomainService implements Vorgangabfrage {

  private final VorgangQuery vorgangQuery;
  private final FachdatenReferenzQuery fachdatenReferenzQuery;
  private final FachdatenAntragstellerQuery fachdatenAntragstellerQuery;
  private final FachdatenFahrzeugQuery fachdatenFahrzeugQuery;

  @Override
  public Vorgang abfragen(String vorgangId) {

    Vorgang vorgang = vorgangQuery.queryById(vorgangId);

    vorgang.fachdatenErgaenzen(ladeFachdaten(vorgang.getFachlicherSchluessel()));

    return vorgang;
  }

  @Override
  public List<Vorgang> abfragenAlle() {
    return vorgangQuery.queryAll().stream()
        .peek(
            vorgang -> vorgang.fachdatenErgaenzen(ladeFachdaten(vorgang.getFachlicherSchluessel())))
        .toList();
  }

  private Fachdaten ladeFachdaten(String fachlicherSchluessel) {
    FachdatenReferenz fachdatenReferenz =
        fachdatenReferenzQuery.queryByFachlicherSchluessel(fachlicherSchluessel);
    FachdatenAntragsteller fachdatenAntragsteller =
        fachdatenAntragstellerQuery.queryByReferenz(fachdatenReferenz.getAntragstellerreferenz());
    FachdatenFahrzeug fachdatenFahrzeug =
        fachdatenFahrzeugQuery.queryByReferenz(fachdatenReferenz.getFahrzeugreferenz());

    return new Fachdaten(fachdatenAntragsteller, fachdatenFahrzeug);
  }
}
