# Verwendung von Arrow Functions als Funktionsdefinitionen

* Status: beschlossen
* Ersetzt:
* Entscheidungsträger:
* Datum: 2025-11-21

## Kontext und Problemstellung

In JavaScript und TypeScript können Funktionen üblicherweise auf zwei Arten definiert werden:

- als klassische Function Declaration:

```ts
function doSomething(a, b) { ...
}
```

- oder als Function Expression per Arrow Function:

```ts
const doSomething = (a, b) => { ...
};
```

Beide Varianten funktionieren technisch einwandfrei.

Es soll entschieden werden, welche Schreibweise zum Standard wird, um Einheitlichkeit, bessere Lesbarkeit und optimale Werkzeugunterstützung zu erzielen.

## Entscheidungsfaktoren

- Klarheit und Lesbarkeit im Codefluss („Top-to-bottom“)
- Gute Unterstützung für TypeScript-Generics
- Tooling-Vorteile (Tree-Shaking, statische Analyse)
- Weniger semantische Fallen durch `this`-Binding
- Einheitliche Schreibweise im gesamten Code

> NOTE
>
> Es gibt eine ESLint-Regel (`func-style`), die mindestens mal überprüft, ob ein einheitlicher Stil eingehalten
> wird: https://eslint.org/docs/latest/rules/func-style

## In Betracht gezogene Optionen

1. Alle Funktionen als `const x = (...) => { ... }` deklarieren
2. Weiterhin Funktionsdeklarationen (`function x(...) { ... }`) verwenden
3. Mischung ohne festgelegten Standard

## Ergebnis der Entscheidung

Gewählte Option:  
**Variantenübergreifender Standard: Funktionen werden per Arrow Function als `const x = (...) => {}` definiert.**

weil

- die Codeausführung klar und linear bleibt,
- TypeScript-Generics leichter und besser lesbar verwendet werden können,
- moderne Bundler und Compiler Arrow Functions besser optimieren können,
- dadurch ein einheitlicher Stil im gesamten Projekt entsteht.

### Positive Folgen

- Einheitliche Funktionsnotation im Code
- Bessere Lesbarkeit und geringere kognitive Last
- Einfachere Typinferenz und Deklaration generischer Funktionen
- Weniger semantische Stolperfallen
- Optimale Unterstützung für Tree-Shaking und Build-Optimierung
- Klarer, moderner funktionaler Code-Stil

### Negative Konsequenzen

- Funktionen können nicht mehr vor ihrer Definition aufgerufen werden (wegen fehlendem Hoisting)
- Erfordert bei einigen Teammitgliedern ggf. Gewöhnung
- In seltenen Sonderfällen müssen Function Declarations weiterhin explizit genutzt werden

## Vor- und Nachteile der Optionen

### Option 1: Arrow Functions als `const`

**Vorteile**

- klarer Programmfluss
- konsistent im funktionalen Stil
- beste Unterstützung für TypeScript-Generics
- gut für static analysis und Tree-Shaking
- kein implizites `this`-Binding

**Nachteile**

- nicht vor Deklaration nutzbar

### Option 2: Klassische Funktionsdeklarationen

**Vorteile**

- sofort aufrufbar (Hoisting)
- traditionelles Funktionsmodell

**Nachteile**

- stilistisch gemischt in funktionalen Codebasen
- teilweise schlechtere Unterstützung bei Generics
- erschwert statische Analyse durch Build-Tools

### Option 3: Keine feste Regel

**Vorteile**

- maximale Freiheit

**Nachteile**

- inkonsistenter Code
- höherer Aufwand bei Reviews
- uneinheitliches mentales Modell

## Links

- https://www.webdevtutor.net/blog/typescript-arrow-vs-function
- https://www.xjavascript.com/blog/arrow-functions-typescript/
- https://www.systemsarchitect.io/docs/guides/typescript-guide/configuration/best-practices-for-functions-and-methods
- https://medium.com/%40jasminbhesaniya/the-complete-guide-to-javascript-functions-and-arrow-functions-d616f2f38811
- https://en.wikipedia.org/wiki/Tree_shaking
- https://krython.com/tutorial/typescript/arrow-functions-in-typescript
