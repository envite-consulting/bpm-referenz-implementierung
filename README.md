<!-- omit in toc -->
# BPM Projekt Referenz-Implementierung

Dieses Repo soll als Blaupause für BPM Projekte dienen. Dabei soll es möglich sein unterschiedliche Technologien wie Camunda, React, Vue etc. nach und nach zu ergänzen.

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
    - [Fremdsoftware frei verfügbar](#fremdsoftware-frei-verfügbar)
- [Kontextabgrenzung](#kontextabgrenzung)
  - [Fachlicher Kontext](#fachlicher-kontext)
  - [Technischer Kontext](#technischer-kontext)
- [Lösungsstrategie](#lösungsstrategie)
- [Bausteinsicht](#bausteinsicht)
  - [Whitebox Gesamtsystem](#whitebox-gesamtsystem)
- [Laufzeitsicht](#laufzeitsicht)
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

Blaupause und Nachschlagewerk für gewisse Fragestellung die in BPM Projekte häufiger auftreten. Zudem soll es dem Onboarding neuer Mitarbeiter dienen.

### Stakeholder

| Rolle        | Kontakt        | Erwartungshaltung |
|--------------|----------------|-------------------|
| *\<Rolle-1>* | *\<Kontakt-1>* | *\<Erwartung-1>*  |
| *\<Rolle-2>* | *\<Kontakt-2>* | *\<Erwartung-2>*  |

## Randbedingungen

### Technisch

#### Verwaltung von Dependencies

Zur Verwaltung von Dependencies soll

- in der Backend-Java-Welt [Maven](https://maven.apache.org/) und
- in der Frontend-Welt [npm](https://www.npmjs.com/)

eingesetzt werden.

#### Fremdsoftware frei verfügbar

Falls zur Lösung Fremdsoftware hinzugezogen wird, sollte diese idealerweise frei verfügbar und kostenlos sein. Die Schwelle der Verwendung wird auf diese Weise niedrig gehalten

## Kontextabgrenzung

### Fachlicher Kontext

**\<Diagramm und/oder Tabelle>**

**\<optional: Erläuterung der externen fachlichen Schnittstellen>**

### Technischer Kontext

**\<Diagramm oder Tabelle>**

**\<optional: Erläuterung der externen technischen Schnittstellen>**

**\<Mapping fachliche auf technische Schnittstellen>**

## Lösungsstrategie

## Bausteinsicht

### Whitebox Gesamtsystem

![Whitebox System](./assets/whitebox-gesamtsystem.svg)



## Laufzeitsicht

## Verteilungssicht

## Querschnittliche Konzepte

## Architekturentscheidungen

## Qualitätsanforderungen

### Qualitätsbaum

### Qualitätsszenarien

## Risiken und technische Schulden

## Glossar

| Begriff        | Definition        |
|----------------|-------------------|
| *\<Begriff-1>* | *\<Definition-1>* |


