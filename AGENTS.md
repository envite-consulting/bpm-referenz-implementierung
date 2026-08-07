---
name: BPM Referenz Implementierung
description: Referenzprojekt für BPM Projekte mit Process Engine, eigener Taskliste
---

# BPM Projekt Referenz-Implementierung

## Projekt Überblick

Dieses Projekt soll als Blaupause für BPM Projekte dienen. Dabei soll es möglich sein unterschiedliche Technologien wie
Camunda, React, Vue etc. nach und nach zu ergänzen. Es soll Fragestellungen, die in BPM Projekten häufiger auftreten nachhaltig und mit Alternativen beantworten.

Das Projekt arbeitet eine Beispiel aus einer Fachlichkeit die den meisten Nutzern geläufig ist:Es betrachtet die Abwicklung von Firmenwagenbestellungen inklusive Bestelldetails, Antragsteller und zugehörigem Fahrzeug umfasst.

## Projektstruktur

**Backend** unterteilt  sich in 2 Bausteine:
1. `backend/business-logic-modulith`
  * Technologie: Multi-Module Maven Project mit Java zur Abbildung des fachlichen Beispiel. Pro Bounded Context ein eigenes Modul. 
  * Architektur:
    * Clean Architecture: Domain (Model + Service) <- Use Case -> Adapter. Keine Infrastruktur in der Domäne, ausgenommen Dependency Injection Mechanismen
    * Bausteine aus dem taktischen DDD:
      * Immutable Domänen Modelle (Value Object, Entity, Aggregate) dir Ihre Invariante abbilden, sich somit selbst validieren.
      * Domänen Events um den Zustand der Domäne zu verändern.
      * Domänen Services für Komplexe Operationen und übergreifende Validierungen.
    * `backend/business-logic-modulith/technical` Modul kapselt technsich-wiederverwendbare Infrastruktur.
2. `backend/remote-engine`: 
  * Technologie: Maven, Java Projekt
  * Sinn und Zweck: Camunda Spring Boot Remote Engine

**Frontend** wird unter `frontend/<FRAMEWOKR>` abgebildet. Aktuell gibt es nur React daher:
* `frontend/react`

## Dokumentation

- Dokumentiert wird in der `README.md` unter der Überschrift `Architekturdokumentation`. Es wird das [arc42](https://arc42.org) format genutzt. Beschrieben werden nur die notwendigen Kapitel. Diagramme werden wenn möglich mit [PlantUML](https://plantuml.com/) umgesetzt.
- Architekturentscheidungen (ADR) werden mit derm Tool [adr-j](https://github.com/adoble/adr-j/tree/main) in `doc/adr/<BAUSTEIN:frontend|backend>` abgelegt und in der `README.md` verlinkt.

## Vorgaben und Qualitätssicherung

- Beachte die Vorgaben in der Architekturdokumentation, spezielle Qualitätsanforderungen, Randbedingungen und Lösungsstrategien. Weiße auf Inkonsitenzen hin. 
- Commits klein und überschaubar halten.
- Jeder Commit muss kompilieren, die Tests müssen durchlaufen und die Dokumentation muss aktuell und konsistent sein.
- Die README.md muss aktuell gehalten werden.

## Qualitätssicherung

**Backend**
- Kompilieren: `mvnw compile`
- Testen: `mvnw test`

**Frontend**
- Linten: `npm run lint`
- Kompilieren: `npm run build`
- Testen: `npm run test`
