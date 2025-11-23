# HowTo ADRs

Mit der [Architekturentscheidung 0001](general/0001-Architekturentscheidungen-dokumentieren.md) ist entschieden, dass jede Architekturentscheidung in einem
separaten File beschrieben wird und wir das Tool [adr-j](https://github.com/adoble/adr-j/tree/main) zum Verwalten der Entscheidungen verwenden.

Das Tool ist leichtgewichtig und intuitiv handhabbar. Hier sind die wichtigsten Punkte zusammengefasst, damit jeder relativ leicht eine Architekturentscheidung
dokumentieren kann.

> NOTE
>
> Das Tool `adr-j` dient als Unterstützung. Es ist aber keine _magic_ dahinter.
>
> Es spricht prinzipiell nichts dagegen, ADRs manuell zu schreiben.
>
> Wenn ein ADR manuell angelegt wird, sollte aber auf jeden Fall das Template [madr.md](templates/madr.md) dazu verwendet werden und die Benennung des neuen
> Files sollte dem Schema `<lfdNr>-<titel>.md` folgen.

Grundsätzlich legen wir Architekturentscheidungen _kontextbezogen_ ab. D.h., Entscheidungen, die für das Frontend gelten, werden im `frontend` Verzeichnis
abgelegt. Entscheidungen für das Backend im `backend` Verzeichnis. Es gibt keine Einschränkungen, was die Struktur angeht. Wichtig ist nur, dass jedes
Verzeichnis so vorbereitet ist, dass das `adr-j` Tool die Entscheidungen verwalten kann.

In jedem Verzeichnis gibt es ein Script (`adr`, bzw. `adr.bat`), welches das `adr-j.jar`ansteuert und die, im Folgenden kurz beschriebenen Befehle ausführt.
Eine Hilfe sämtlicher Befehle erhält man mit

```shell
./adr help
```

## Die wichtigsten Befehle für die tägliche Arbeit

```shell
./adr new Eine neue Architekturentscheidung
```

* Legt ein neues File `<lfdNr>-eine-neue-architekturentscheidung.md` an und öffnet den Editor mit diesem File. Das File entspricht einem konfigurierten
  Template.

```shell
./adr new -s 0008 Eine neue Architekturentscheidung die 0008 ersetzt
```

* Wie der `new`-Befehl, nur dass automatisch ein weiterer Eintrag gemacht wird, der aussagt, dass die neue Architekturentscheidung eben jene mit der Nummer 0008
  aufhebt
    * > NOTE
      >
      > Was das Tool leider nicht kann ist, in der ersetzten Architekturentscheidung auch den Hinweis zu schreiben, dass diese durch Architekturentscheidung xy
      ersetzt wurde
      >
      > Dieser Hinweis muss also manuell gepflegt werden

```shell
./adr generate toc
```

* Erzeugt ein `toc.md` mit der Liste ADRs in dem Verzeichnis