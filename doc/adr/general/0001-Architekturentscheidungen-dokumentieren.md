# Architekturentscheidungen dokumentieren

* Status: akzeptiert

* Datum: 23.11.2025

## Kontext und Problemstellung

Entscheidungen, die sich auf die Architektur auswirken können, sind Architekturentscheidungen. Folgendes sollte so einfach wie möglich sein:

1. Die Entscheidungen aufschreiben
2. Die Entscheidungen versionieren

## Entscheidungsfaktoren

* Architekturentscheidungen sollten kurz und prägnant sein.
* Die Architekturentscheidung sollte in Befehls-Softwareentwicklungswerkzeugen wie [Git](https://git-scm.com/) versionierbar sein.
* Die Verwaltung der Architekturentscheidungen (z. B. Nummerierung, Datierung, Verknüpfungen) sollte durch ein Tool unterstützt werden.
* Eine Architekturentscheidung sollte nach einer Vorlage erstellt werden
* Architekturentscheidungen sollten untereinander verlinkt werden können; es soll erkennbar sein, ob z.B. eine Architekturentscheidung eine andere aufhebt

## In Betracht gezogene Optionen

* OPTION 1: Sammeln der Architekturentscheidungen in einem einzigen Dokument.
* OPTION 2: Sammeln der Architekturentscheidung in separaten Dateien, die versioniert werden können, d. h. als leichtgewichtiges
  Architekturentscheidungsprotokoll (ADR), und diese manuell verwalten
* OPTION 2: Verwendung eines Tools zur Unterstützung der Erstellung und Verwaltung von ADRs.

## Ergebnis der Entscheidung

Gewählte Option: Mischung aus OPTION 1 und OPTION 2 ("Sammeln der Architekturentscheidung in separaten Dateien" und "Verwendung eines Tools zur Unterstützung
der Erstellung und Verwaltung von ADRs")

1. Wir verwenden das [MADR](https://adr.github.io/madr/)-Format, um die Architekturentscheidungen prägnant zu dokumentieren (eine entsprechende Vorlage liegt im
   `templates`-Verzeichnis: [madr.md](../templates/madr.md) oder [madr_withHelp.md](../templates/madr_withHelp.md))
2. Jede Entscheidung wird in einem eigenen File beschrieben
3. Die Entscheidungen werden in einem _ToC_ gelistet
3. Wir verwenden das Tool [adr-j](https://github.com/adoble/adr-j), um leichtgewichtige ADRs zu schreiben und zu verwalten. Dieses Tool ist die JAVA-Version
   der [adr-tools](https://github.com/npryce/adr-tools) von Nat Pryce und bietet sich an, da der gesamte technische Kontext des Projektes durchaus auch
   JAVA-Bestandteile enthält und somit die JAVA-Runtime vorhanden ist.
4. Die Basis dieser Entscheidung sind die Gedanken und Empfehlungen
   von [Michael Nygard](https://cognitect.com/blog/2011/11/15/documenting-architecture-decisions).