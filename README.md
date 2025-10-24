<!-- omit in toc -->
# BPM Projekt Referenz-Implementierung

Dieses Repo soll als Blaupause für BPM Projekte dienen. Dabei soll es möglich sein unterschiedliche Technologien wie
Camunda, React, Vue etc. nach und nach zu ergänzen.

Die Dokumentation des Projekts erfolgt nach [arc42](https://arc42.org/).

<!-- omit in toc -->
## Architekturdokumentation

**Über arc42**

arc42, das Template zur Dokumentation von Software- und
Systemarchitekturen.

Template Version 8.2 DE. (basiert auf AsciiDoc Version), Januar 2023

Created, maintained and © by Dr. Peter Hruschka, Dr. Gernot Starke and
contributors. Siehe <https://arc42.org>.

- [Einführung und Ziele](#einführung-und-ziele)
  - [Qualitätsziele](#qualitätsziele)
  - [Stakeholder](#stakeholder)
- [Randbedingungen](#randbedingungen)
  - [Technisch](#technisch)
    - [Verwaltung von Dependencies](#verwaltung-von-dependencies)
    - [Spezifische technische Randbedingungen](#spezifische-technische-randbedingungen)
    - [Fremdsoftware frei verfügbar](#fremdsoftware-frei-verfügbar)
- [Kontextabgrenzung](#kontextabgrenzung)
  - [Fachlicher Kontext](#fachlicher-kontext)
  - [Technischer Kontext](#technischer-kontext)
- [Lösungsstrategie](#lösungsstrategie)
  - [Backend](#backend)
    - [Clean Architecture](#clean-architecture)
      - [Bausteinsicht](#bausteinsicht)
        - [Beispiel: Zu detaillierte Darstellung](#beispiel-zu-detaillierte-darstellung)
      - [Laufzeitsicht](#laufzeitsicht)
    - [Dependency Inversion Principle](#dependency-inversion-principle)
    - [Mapping zwischen Schichten](#mapping-zwischen-schichten)
    - [Domain Driven Design](#domain-driven-design)
    - [Besondere ID-Behandlung:](#besondere-id-behandlung)
    - [Exception Handling](#exception-handling)
  - [Frontend](#frontend)
    - [Atomic Design](#atomic-design)
    - [TypeScript Pfad-Aliases](#typescript-pfad-aliases)
    - [Paketierung](#paketierung)
- [Bausteinsicht](#bausteinsicht-1)
  - [Blackbox Gesamtsystem](#blackbox-gesamtsystem)
    - [Ebene 1: Graybox Gesamtsystem](#ebene-1-graybox-gesamtsystem)
      - [Ebene 2: Whitebox Fachbausteine](#ebene-2-whitebox-fachbausteine)
- [Laufzeitsicht](#laufzeitsicht-1)
- [Verteilungssicht](#verteilungssicht)
- [Querschnittliche Konzepte](#querschnittliche-konzepte)
- [Architekturentscheidungen](#architekturentscheidungen)
- [Qualitätsanforderungen](#qualitätsanforderungen)
  - [Qualitätsbaum](#qualitätsbaum)
  - [Qualitätsszenarien](#qualitätsszenarien)
- [Risiken und technische Schulden](#risiken-und-technische-schulden)
- [Glossar](#glossar)

## Einführung und Ziele

### Qualitätsziele

Blaupause und Nachschlagewerk für gewisse Fragestellungen, die in BPM Projekten häufiger auftreten. Zudem soll es dem
Onboarding neuer Mitarbeiter dienen.

### Stakeholder

| Rolle        | Kontakt        | Erwartungshaltung |
|--------------|----------------|-------------------|
| *\<Rolle-1>* | *\<Kontakt-1>* | *\<Erwartung-1>*  |
| *\<Rolle-2>* | *\<Kontakt-2>* | *\<Erwartung-2>*  |

## Randbedingungen

### Compliance

#### Keine Kundebeispiele oder -daten

Alle hier verwendeten Lösungsansätze und Muster müssen pseudonymisiert und abstrahiert sein.

### Technisch

#### Verwaltung von Dependencies

Zur Verwaltung von Dependencies soll

- in der Backend-Java-Welt [Maven](https://maven.apache.org/) und
- in der Frontend-Welt [npm](https://www.npmjs.com/)

eingesetzt werden.

#### Spezifische technische Randbedingungen

[Spring Boot](https://spring.io/projects/spring-boot): Als Application Framework
[Spring Data JDBC](https://spring.io/projects/spring-data-jdbc) + [Liquibase](https://www.liquibase.com/): Für
Datenbankanbindung und Schema-Migration
[domain-primitives-java](https://github.com/domain-primitives/domain-primitives-java): Für Validierung der
Domain-Objekte
[MapStruct](https://mapstruct.org/): Für Mapping zwischen Domain-Objekten und Resources

#### Fremdsoftware frei verfügbar

Falls zur Lösung Fremdsoftware hinzugezogen wird, sollte diese idealerweise frei verfügbar und kostenlos sein. Die
Schwelle der Verwendung wird auf diese Weise niedrig gehalten

## Kontextabgrenzung

### Fachlicher Kontext

![Kontext Map](/assets/kontextabgrenzung/fachlicher-kontext-map/fachlicher-kontext-map.svg)

Der dargestellte fachliche Kontext zeigt mehrere Bereiche des Unternehmens. In diesem Projekt wird der
Bestellung-Kontext betrachtet, der die Abwicklung von Firmenwagenbestellungen inklusive Bestelldetails, Antragsteller
und zugehörigem Fahrzeug umfasst.

### Technischer Kontext

![Technischer Kontext Map](assets/kontextabgrenzung/technischer-kontext-map/technischer-kontext-map.svg)

| Fachlicher Kontext                        | Technische Schnittstelle     | Technische Details                                                             |
|-------------------------------------------|------------------------------|--------------------------------------------------------------------------------|
| Mitarbeiter-Kontext                       | Mitarbeiter-Kontext          | In dieser Beispielimplementierung nicht angebunden, Testdaten in DB            |
| Flottenmanagement-Kontext (Fahrzeug)      | Flottenmanagement-Kontext    | In dieser Beispielimplementierung nicht angebunden, Testdaten in DB            |
| Flottenmanagement-Kontext (CO₂-Bewertung) | CO₂-API                      | REST-API Aufruf an api-carbonsutra.com                                         |
| Bestellung-Kontext                        | bpm-referenz-implementierung | Kern des aktuellen Systems (business-logic-modulith + Camunda 7 Remote Engine) |

## Lösungsstrategie

### Backend

#### Clean Architecture

Eine Softwarearchitektur, die Veränderbarkeit in ihre Paket- und Klassenstruktur integriert, erleichtert den Wechsel
oder die Migration eines Frameworks. Daher ist die bspw. Migration von Camunda Platform 7 auf 8 dank einer guten
Architektur wesentlich weniger aufwendig.

![DDD-Clean-Architecture](assets/loesungsstrategie/camunda-ddd-and-clean-architecture-rings.png)
*Die Ringe der Clean Architecture (basierend auf Clean Architecture by Robert C. Martin)*

Robert C. Martin beschreibt in seinem
Buch [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html) architektonische
Richtlinien, die die Unabhängigkeit von Frameworks, Datenbanken, Benutzeroberflächen (UI) und anderen Technologien
gewährleisten sollen. Seiner Meinung nach gewährleistet eine saubere Architektur durch ihr Design die Testbarkeit von
Geschäftsregeln.

Die Grafik oben zeigt Schichten als konzentrische Ringe, die sich gegenseitig umschließen. Jede Schicht repräsentiert
verschiedene Teile der Software. Der Mittelpunkt der Ringe steht die Geschäftsregeln und Ihr Domänenwissen. Die äußeren
Ringe sind „Mechanismen”, die unser Domänenzentrum unterstützen. Neben den Schichten zeigen die Pfeile die
Abhängigkeitsregel - nur nach innen gerichtete Abhängigkeiten (
siehe [Dependency Inversion Principle](#dependency-inversion-principle))!

Um die Ziele einer sauberen Architektur zu erreichen, darf der Domänencode keine nach außen gerichteten Abhängigkeiten
aufweisen. Stattdessen zeigen alle Abhängigkeiten auf den Domänencode. Der wesentliche Aspekt Ihrer Geschäftsdomäne
befindet sich im Kern der Architektur: die Entitäten. Auf diese wird nur von der umgebenden Schicht zugegriffen: den
Anwendungsfällen. Services in einer klassischen Schichtenarchitektur entsprechen Anwendungsfällen in einer sauberen
Architektur, aber diese Services sollten feiner gegliedert sein, sodass sie nur eine einzige Aufgabe haben. Es sollte
nicht, ein einziger großer Service alle Ihre Geschäftsanwendungsfälle implementiert. Unterstützende Komponenten wie
Persistenz oder Benutzeroberflächen sind um den Kern (Ihre Entitäten und Anwendungsfälle) herum angeordnet.

##### Bausteinsicht

Die [Bausteinansicht](https://docs.arc42.org/section-5/)“ unten zeigt eine stereotype statische Zerlegung eines Systems
unter Verwendung der Clean Architecture in Bausteine sowie deren Abhängigkeiten.

![Clean-Architecture-Building-Block-View](assets/loesungsstrategie/clean_architecture_building_blocks.svg)
*Building Block View of Clean Architecture*

> [!NOTE]  
> Da die Bausteinsicht einen schnellen, abstrahierten Überblick über ein Projekt oder Modul vermitteln soll, ist eine
> bewusst gewählte, sinnvolle Abstraktion entscheidend. Insbesondere deshalb, weil diese Ansicht derzeit nicht
> automatisiert generiert wird und daher potenziell schnell veralten kann.
>
>Ein zu hoher Detailgrad wie etwa in der folgenden Darstellung sollte also vermieden werden.

###### Beispiel: Zu detaillierte Darstellung

![Blackbox_Bestellung](assets/bausteinsicht/fachbausteinfalscherdetailgrad/whitebox-bestellung-falscher-detailgrad.svg)

##### Laufzeitsicht

Die [Laufzeitansicht](https://docs.arc42.org/section-6/) unten beschreibt das konkrete Verhalten und die Interaktionen
der stereotypen Bausteine eines Systems, das Clean Architecture verwendet.

![Clean-Architecture-Runtime-Block-View](assets/loesungsstrategie/clean_architecture_runtime_view.svg)
*Building Block View of Clean Architecture*

> [!NOTE]  
> Da die Laufzeitansicht eng an konkrete Implementierungsdetails gekoppelt ist, ist ihr Pflegeaufwand entsprechend hoch.
> Sie sollte daher gezielt für fachlich relevante oder komplexe Abläufe eingesetzt werden.
> Einfache CRUD-Operationen hingegen sollten nicht in Laufzeitdiagrammen dargestellt werden.

#### Dependency Inversion Principle

Durch Anwendung der Abhängigkeitsregel erhält die Domäne keine Kenntnis darüber, wie Sie Ihre Daten speichern oder wie
Sie sie in einem Client anzeigen.
Die Domäne sollte keinen Framework-Code enthalten (außer evt. Dependency Injection). Wie bereits erwähnt, können Sie das
Dependency Inversion Principle (DIP) verwenden, um die Abhängigkeitsregel der [Clean Architecture](#clean-architecture)
anzuwenden.

Das DIP besagt, dass Sie die Richtung einer Abhängigkeit umkehren sollen. Vielleicht denken Sie an das Entwurfsmuster
Inversion of Control (IoC), das nicht mit DIP identisch ist, obwohl beide gut zusammenpassen. Für die genauen
Unterschiede empfehle ich Ihnen den Artikel von Martin
Fowler [DIP in the Wild](https://martinfowler.com/articles/dipInTheWild.html#YouMeanDependencyInversionRight) (kurz
gesagt: „[...] Bei IoC geht es um die Richtung, bei DIP um die Form.“). Die folgende Abbildung zeigt ein Beispiel dafür,
wie das DIP funktioniert.

![Mit und ohne Dependency Inversion Principle](assets/loesungsstrategie/dependency-inversion-principle.png)
*Das Dependency Inversion Principle (DIP)*

Stellen Sie sich einen Service (DomainService in der Abbildung) vor, der einen Camunda-Prozess startet. Um Ihren
Service (Ihre Geschäftslogik) vom Framework zu isolieren, könnten Sie einen weiteren Service mit der Camunda Java API
erstellen, um eine Prozessinstanz zu starten. Der linke Rahmen der Abbildung zeigt dieses Szenario ohne Anwendung des
DIP. Der Domain-Service ruft den ProcessEngineService direkt auf. Wo liegt also das Problem? Das Starten eines Prozesses
ist ein zentraler Aspekt von

Durch die Kombination des DIP mit der [Ports und Adapter](http://alistair.cockburn.us/Hexagonal+architecture)
Architektur (aus der die saubere Architektur hervorgegangen ist) erhalten wir die unten gezeigte Grafik.

![DIP, Ports und Adapter, saubere Architektur](assets/loesungsstrategie/dependency-inversion-principle-rings-in-out.png)
*Saubere Architektur DIP und Ports und Adapter*

Die Trennung unserer Ports/Anwendungsfälle und Adapter, die unsere Anwendung steuern (Eingangsports, Driving) oder von
unserer Anwendung gesteuert werden (Ausgangsports, driven), hilft uns, unseren Code noch besser zu strukturieren und die
Grenzen klarer zu halten.

#### Mapping zwischen Schichten

Die nachfolge Grafik zeigt, wie die Schichten mit dem Domänenobjekt mit und ohne Zuordnung interagieren. Ohne Zuordnung
geht einer der größten Vorteil der [Clean Architecture](#clean-architecture) verloren: die Entkopplung Ihres
Domänenkerns von den äußeren (Infrastruktur-)Schichten. Wenn man keine Zuordnung zwischen den inneren und äußeren
Schichten vornimmt, sind diese nicht isoliert. Wenn ein System eines Drittanbieters sein Datenmodell ändert, muss man
auch sein Domänenmodell ändern.

Um Abhängigkeiten von externen Einflussfaktoren zu vermeiden und Unabhängigkeit und Entkopplung zu fördern, ist es
notwendig, zwischen den Schichten zu mappen. Die Eingabe- und Ausgabeports (Use-Case-Schicht) dienen als Gatekeeper zu
Ihrem Domänenkern und definieren, wie mit Ihrer Anwendung kommuniziert und interagiert wird. Sie bieten eine klare API,
und durch das Mapping in Ihre Domäne halten Sie diese unabhängig von Änderungen am Framework oder Technologiemodell.

Für das automatisierte und typsichere Erstellen von Mappern wird in diesem Projekt MapStruct eingesetzt. Für jede
Technologie der Adapterschicht und getrennt nach eingehendem und ausgehendem Datenfluss wird ein eigenes DTO mit
zugehörigem Mapper-Interface definiert.

![Mit und ohne Zuordnung zwischen den Schichten](assets/loesungsstrategie/mapping-between-layers.png)
*Mit und ohne Zuordnung zwischen den Schichten*

Der Rahmen, der den Mapping-Ansatz erklärt, ist nur eine von vielen Möglichkeiten, Mapping durchzuführen. Um mehr über
die verschiedenen Varianten des Mappings zu erfahren, empfiehlt sich Tom Hombergs
Buch [Get Your Hands Dirty on Clean Architecture](https://leanpub.com/get-your-hands-dirty-on-clean-architecture), in
dem er diese sehr gut erklärt.

Zusammenfassend lässt sich sagen, dass Mapping verwendet werden kann, um eine stärkere Entkopplung zu erreichen.
Andererseits kann das Mapping zwischen den einzelnen Schichten zu einer Menge Boilerplate-Code führen, der zwar durch
Tools wie MapStruct minimiert werden kann, was je nach Anwendungsfall und den Zielen, die Sie verfolgen, aber
übertrieben sein könnte.

Innerhalb dieses Projektes wird ein

#### Domain Driven Design

Die Verwendung einer [Clean Architecture](#clean-architecture) als Architekturstil lässt sich perfekt mit Domain-driven
Design kombinieren, da wir uns vollständig auf unseren Domänenkern (Entitäten und Anwendungsfälle) konzentrieren. Die
Konzentration auf die Domäne wird durch das Ziel von [Clean Architecture](#clean-architecture) unterstützt, die Domäne
frei von Frameworks oder Technologien zu halten. Beispielsweise konzentriert sich Ihre Domäne nicht darauf, wie etwas
gespeichert wird, sondern weist lediglich den ausgehenden Port an, es zu speichern. Die Implementierung des Ports (auf
der Adapter-Ebene) entscheidet, ob beispielsweise relationale oder nicht-relationale Datenbanken verwendet werden.

Neben dem übereinstimmenden Ziel von DDD und [Clean Architecture](#clean-architecture) versucht DDD dabei zu helfen,
komplexe Designs rund um Ihre Domäne zu erstellen, indem man beispielsweise unveränderliche Objekte erstellt, die alles
über ihre Invarianten wissen, was noch mehr dabei hilft, den Code zu strukturieren.

DDD-Elemente wie Aggregate Entities und ValueObject werden aus der
Bibliothek [domain-primitives](https://github.com/domain-primitives/domain-primitives-java) genutzt.

Zur Absicherung der Domäne gegen ungültige Zustände werden in diesem Projekt die Constraints dieser Bibliothek
eingesetzt. Sie stellen sicher, dass alle Objekte bereits beim Erstellen ihre Invarianten erfüllen, sowohl bei Value
Objects als auch bei Aggregates.

**Value Object Validierung**

Value Objects validieren ihre Werte anhand definierter Invarianten:

```java
// ValueObjectId: Muss gültige UUID sein
public class ValueObjectId extends ValueObject<String> {
    public ValueObjectId(String value) {
        super(value, isUUID());
    }
}
```

**Aggregate-Validierung**

Aggregates validieren die Vollständigkeit der aggregierten Value Objects und Entitäten:

```java
// Entity-Validierung auf höchster Ebene
@Override
protected void validate() {
    validateNotNull(referenz1, "Referenz 1");
    validateNotNull(valueObject2, "Value Object 2");
    evaluateValidations();
}
```

Die funktionale Strukturierung des Codes und die Einbringung von mehr Kontext in die Objekt, z. B. mit Value Object,
hilft nicht nur, den Code ausdrucksstark zu halten, sondern auch, ihn nah an den Geschäft zu halten, wie auch das
BPMN-Modell.

#### Behandlung der ID beim Persistieren in der Domäne:

Beim Persistieren von neuen Entitäten ohne initiale ID kann das ID-Handling nach 3 Varianten gelöst werden:

**Variante 1:** Erzeugen der ID in der Domäne zusammen mit dem Objekt

- Die Domäne bietet 2 Konstruktoren an:
    - einen ohne ID: Hier wird erzeugt, das heißt eine ID vergeben.
    - einen mit ID: Hier kann die ID aus beim Laden gesetzt werden.

**Variante 2:** Externe Erzeugung: Konstruktorbasierte ID-Initialisierung

- Zwei Konstruktoren: mit und ohne ID
- Der Mapper entscheidet zur Laufzeit anhand der ID-Präsenz, welcher Konstruktor verwendet wird (@ObjectFactory)

**Variante 3:** Externe Erzeugung: Setter-basierte ID-Zuweisung

- Es existiert ein Konstruktor ohne ID
- Die ID wird nach der Initialisierung per Setter gesetzt
- Die Zuweisung erfolgt im Mapper implizit durch MapStruct

#### Exception Handling

Die Fehlerbehandlung auf REST-Ebene erfolgt über einen @ControllerAdvice[r] pro Fachmodul. Exceptions aus der Domäne
werden dabei in standardisierte HTTP-Antworten überführt:

- InvariantException → 400 Bad Request
- EntityNotFoundException → 404 Not Found
- PersistenceException → 500 Internal Server Error

Jeder Fehlerfall wird in ein strukturiertes Fehlerobjekt umgewandelt, das Name, Nachricht und ggf. Fehlerursache
enthält.

### Frontend

#### Atomic Design
Das Frontend folgt dem [Atomic Design-Prinzip](https://atomicdesign.bradfrost.com/chapter-2/), um eine konsistente, wiederverwendbare und skalierbare UI-Architektur sicherzustellen. Komponenten werden in hierarchische Ebenen (Atoms, Molecules, Organisms, Templates, Pages) unterteilt. Dadurch wird die Wiederverwendung gefördert, die Wartbarkeit erhöht und ein einheitliches Design über die gesamte Anwendung gewährleistet.

#### TypeScript Pfad-Aliases
Zur Verbesserung der Code-Lesbarkeit und Reduzierung fehleranfälliger relativer Importpfade werden in der tsconfig.json [TypeScript-Aliases](https://blog.logrocket.com/using-path-aliases-cleaner-react-typescript-imports/) definiert. Anstatt komplexer relativer Pfade (z. B. `../../../components/Button`) können eindeutige, projektweite Aliase verwendet werden (z. B. `@components/Button`). Dies erhöht die Navigierbarkeit des Codes und erleichtert Refactorings.

#### Paketierung

Die Projektstruktur ist klar in logische Module gegliedert:

`src/pages` enthält alle als Routen definierte Seitendefinitionen und deren spezifische Kind-Komponenten.
z.B.
```
├── pages
│   └── Bestellung
│       ├── Bestellung.test.tsx
│       ├── Bestellung.tsx
│       ├── Bestellung.types.ts // Schemata und Type-Ableitung
│       └── queries
│           ├── api
│           │   ├── fetchBestellung.test.ts
│           │   └── fetchBestellung.ts
│           ├── useBestellungQuery.test.ts
│           └── useBestellungQuery.ts
│       └── components
│           └── Eintrag
│               ├── Eintrag.test.tsx
│               └── Eintrag.tsx
```

`src/infrastructure` kapselt wiederverwendbare Komponenten, Queries etc. und dient damit quasi als interne Library.
z.B.
```
├── infrastructure/components
│   └── Button
│       ├── Button.test.tsx
│       ├── Button.stories.tsx
│       └── Button.tsx
```

## Bausteinsicht

### Blackbox Gesamtsystem

![Blackbox_Gesamtsystem](assets/bausteinsicht/gesamtsystem/blackbox-gesamtsystem.svg)

#### Ebene 1: Graybox Gesamtsystem

![Graybox_Gesamtsystem](assets/bausteinsicht/gesamtsystem/graybox-gesamtsystem.svg)

##### Ebene 2: Whitebox Fachbausteine

![Whitebox_Fachbausteine](assets/bausteinsicht/fachbaustein/whitebox-fachbaustein.svg)

## Laufzeitsicht

## Verteilungssicht

### Verteilungssicht lokale Entwicklung

![Verteilungssicht_lokale_Entwicklung](assets/verteilungssicht/verteilungssicht-lokale-entwicklung.svg)
## Querschnittliche Konzepte

## Architekturentscheidungen

## Qualitätsanforderungen

### Qualitätsbaum

Nachfolgend wird der Qualitätsbaum, angelehnt an [quality.arc42](https://quality.arc42.org/), dargestellt. 

![qualitaetsbaum.svg](assets/qualitaetsanforderungen/qualitaetsbaum.svg)

### Qualitätsszenarien

#### Zuverlässigkeit
##### Verfügbarkeit
###### Verfügbarkeit Montag - Donnerstag von 12 bis 22 Uhr

| **Aspekt**               | **Beschreibung**                                                                                                                               |
|--------------------------|------------------------------------------------------------------------------------------------------------------------------------------------|
| **Quelle des Auslösers** | Arbeitsrichtlinien                                                                                                                             |
| **Auslöser**             | Das System wird von mehreren Nutzern (4-stellige Zahl) täglich aufgerufen.                                                                     |
| **Umgebung**             | Production; Normalbetrieb                                                                                                                      |
| **Systembestandteil**    | Komponenten zur Abwicklung des fachlichen Prozesses ([Backend](#backend), [Frontend](#frontend)), ausgenommen sind administrative Komponenten. |
| **Antwort**              | Die fachlichen funktionen stehen zur Verfügung.                                                                                                |
| **Antwortmetrik**        | Die Komponenten zur Abwicklung des fachlichen Prozessen sind von Montag bis Freitag von 12 bis 22 Uhr verfügbar.                               |


#### Flexibilität
##### Anpassbarkeit
###### Unabhängige Ersetzung von externen Schnittstellen

| **Aspekt**               | **Beschreibung**                                                                                                                                                             |
|--------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Quelle des Auslösers** | Entwicklungsteam / Schnittstellenanbieter                                                                                                                                    |
| **Auslöser**             | Extern genutzte Schnittstellen änderten sich häufig.                                                                                                                         |
| **Umgebung**             | Alle                                                                                                                                                                         |
| **Systembestandteil**    | [Backend](#backend)                                                                                                                                                          |
| **Antwort**              | Lediglich der Schnittstellen-Adapter muss bei einer Änderung der externen Schnisttstelle angepasst werden.                                                                   |
| **Antwortmetrik**        | Die Anpassung ist lokal auf den Adapter der Schnittstelle beschränkt, d.h. die Domäne wird nicht angepasst und das Frontend hat gar keinerleie Kenntnisse von der Anpassung. |

#### Benutzbarkeit
##### Barrierefreiheit
###### Einhaltung der WCA Guidelines

| **Aspekt**               | **Beschreibung**                                                                                                                                                                                                                                                                                                                                |
|--------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Quelle des Auslösers** | Gesetzliche Vorgabe                                                                                                                                                                                                                                                                                                                             |
| **Auslöser**             | Benutzer mit Behinderungen möchte in einer Webanwendung navigieren und mit ihr interagieren.                                                                                                                                                                                                                                                   |
| **Umgebung**             | Alle                                                                                                                                                                                                                                                                                                                                            |
| **Systembestandteil**    | [Frontend](#frontend)                                                                                                                                                                                                                                                                                                                           |
| **Antwort**              | <ul><li>Navigation: Mindestens 95% aller interaktiven Elemente (Links Schaltflächen, etc.) sind mit dem Screenreader navigierbar und identifizierbar (auch bei einer Zoomstufe von 400%)</li><li>Inhalt: Mindestens 98% aller Inhalte einschließlich Text, Bilder, Videos usw., sind mit dem Screenreader zugänglich und konsumierbar</li></ul> |
| **Antwortmetrik**        | Die Webanwendung soll die [WCAG 2.2](https://www.w3.org/TR/WCAG22/) Level A-Standardkriterien erfüllen.                                                                                                                                                                                                                                         |

#### Benutzerfreundlichkeit
###### Selbsterklärende UI

| **Aspekt**               | **Beschreibung**                                                                                                                                                   |
|--------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Quelle des Auslösers** | Nutzer                                                                                                                                                             |
| **Auslöser**             | Benutzung der Webanwendung                                                                                                                                         |
| **Umgebung**             | Alle                                                                                                                                                               |
| **Systembestandteil**    | [Frontend](#frontend)                                                                                                                                              |
| **Antwort**              | Als (neuer) Nutzer aus der anderen Abteilungen will ich mich in der Anwendung zurechtfinden, sodass ich den Bestellprozess ohne Benutzerhandbuch durchführen kann. |
| **Antwortmetrik**        | Die Erfassung einer Bestellung als neuer Benutzer ist in 95% der Fälle in unter 5 Minuten oder weniger möglich.                                                    |

#### Betriebssicherheit (safe)
#### Betriebliche Einschränkung
###### Wenig Deployment Artefakte

| **Aspekt**               | **Beschreibung**                                                                                                                                                     |
|--------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Quelle des Auslösers** | Betrieb                                                                                                                                                              |
| **Auslöser**             | Der Betrieb installiert die Anweundung in der Produktion                                                                                                              |
| **Umgebung**             | Produktion                                                                                                                                                           |
| **Systembestandteil**    | [Bausteinsicht](#bausteinsicht)                                                                                                                                      |
| **Antwort**              | Da der Betrieb das Deployment möglicherweise händisch, mit einer Anleitung durch das Entwicklungsteam, durchführt, muss das Deployment "einfach" sein.               |
| **Antwortmetrik**        | Die Systembestandteile sind auf eine annehmbares Mindestmaß reduziert. Für das Deployment liegen Automatisierungsmechanismen vor, um das Deployment zu vereinfachen. |

#### Nutzungssicherheit (secure)
##### Revisionssicherheit
###### Unveränderbare Daten nach Abschluss

| **Aspekt**               | **Beschreibung**                                                                                                                                                                                                                                               |
|--------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Quelle des Auslösers** | Gesetzliche Vorgabe                                                                                                                                                                                                                                            |
| **Auslöser**             | Eine Bestellung wird abgeschlossen.                                                                                                                                                                                                                            |
| **Umgebung**             | Produktion                                                                                                                                                                                                                                                     |
| **Systembestandteil**    | [Backend](#backend)                                                                                                                                                                                                                                            |
| **Antwort**              | Eine abgeschlossene Bestellung darf nachträglich nicht mehr bearbeitet werden können. Es dürfen lediglich Dokumente hinzugefügt werden.                                                                                                                        |
| **Antwortmetrik**        | Die nachträgliche Bearbeitung einer abgeschlossenen Bestellung ist nicht mehr möglich. Auch die Prozess Daten sind nach dem Abschluss nicht mehr änderbar. Das hinzufügen von Dokumente zu einer abgeschlossen Bestellung ist innerhalb von 4 Monaten möglich. |

#### Effizienz

##### Energieeffizienz / Nachhaltigkeit

Der **Energieverbrauch** wird als zusätzliches Bewertungskriterium für Features berücksichtigt. Im Sinne des **magischen Dreiecks** des Projektmanagements stehen dabei vor allem die Aspekte **Wartbarkeit** und **Performance** im Vordergrund.

| **Aspekt**               | **Beschreibung**                                                                                                                                                                                                             |
|--------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Quelle des Auslösers** | Entwicklungsteam / Product Owner                                                                                                                                                                                             |
| **Auslöser**             | Bewertung eines neuen oder bestehenden Features im Hinblick auf Energieverbrauch                                                                                                                                             |
| **Umgebung**             | Entwicklungs- und Betriebsumgebung des Systems (z. B. CI/CD-Pipeline, Produktionsumgebung)                                                                                                                                   |
| **Systembestandteil**    | Gesamtsystem bzw. betroffene Softwarekomponenten, deren Implementierung Energieverbrauch beeinflusst                                                                                                                         |
| **Antwort**              | Das System bzw. Team berücksichtigt den Energieverbrauch als zusätzliches Qualitätskriterium bei der Feature-Bewertung. Dabei stehen **Wartbarkeit** und **Performance** im Sinne des **magischen Dreiecks** im Vordergrund. |
| **Antwortmetrik**        | Dokumentierte Entscheidung im Architektur-Review (z. B. ADR); Nachweis messbarer Energieeffizienz (z. B. Energieverbrauch pro Transaktion / Request) in Kombination mit akzeptabler Performance.                             |

## Risiken und technische Schulden

## Glossar

| Begriff | Definition        |
|---------|-------------------|
| *WCAG*  | Web Content Accessibility Guidelines |


