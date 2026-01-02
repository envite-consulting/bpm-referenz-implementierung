# Query-Resultate einheitlich verarbeiten

## Problemstellung

Das Frontend löst an unterschiedlichen Stellen Queries zum Backend aus. Diese Queries werden asynchron ausgeführt und
von [tanstack/useQuery](https://tanstack.com/query/v5/docs/framework/react/reference/useQuery) gekapselt.

Während der Ausführung eines Queries gibt es unterschiedliche Status, die von der Anwendung einheitlich behandelt werden sollen:

* Fetching - wenn der Query gerade Daten lädt
* Error - wenn der Query auf einen Fehler läuft
* Success - wenn der Query erfolgreich ausgeführt wurde

Darüber hinaus kann ein `useQuery` unterschiedlich konfiguriert werden (Stw. Caching, Refetch, ...). Nicht jede Möglichkeit wird in der Anwendung jedoch
gebraucht und die manchmal komplexen Konfigurationsmöglichkeiten müssen pro Query durchgeführt werden.

Die Implementierung eines Queries kann daher immer wieder zu neuen Herausforderungen führen und insbesondere die Reaktion auf die unterschiedlichen Status muss
immer wieder implementiert werden, wenngleich sie einheitlich implementiert werden sollen.

## Lösungsstrategie

1. Der `useQuery` wird in applikationsrelevanten Funktionen gekapselt, die anwendungsfallspezifisch parameteresiert sind und den Status des Queries vereinfacht,
   den Anwendungsfällen der Anwendung entsprechenden Situationen zurückgibt.
    2. Im Wesentlichen muss ein Query in der Anwendung eindeutig identifizierbar sein
    3. Die Anwendung braucht nur auf die Status 'Fetching', 'Error' und 'Success' reagieren (weitere Status von `useQuery`, wie `isLoadingError` können in der
       Anwendung auf 'Error' gemapped werden)
4. Für das Behandeln des Status wird eine React-Komponente gebaut, die je nach Status immer die gleiche Komponente retourniert und im Erfolgsfall ('Success')
   die Kind-Komponente anzeigt

## Pattern

1. Es gibt einen _Custom-Hook_, der den `useQuery` kapselt

```typescript
export const useQueryBoundaryFn = <TData>({queryKey, queryFn, ...}: QueryBoundaryFnProps<TData>): QueryBoundaryFnResult<TData> => {
...
}
```

2. Es gibt eine React-Komponente, die den Query-Status einheitlich abhandelt:

```typescript jsx
export const QueryBoundary = <TData, >({
                                           queryResult,
                                           children,
                                           errorType,
                                       }: QueryBoundaryProps<TData>): React.ReactNode => {
    const {isFetching, isError, getErrorMessage} = queryResult;

    if (isFetching) return <LoadingSpin/>;
    if (isError)
        return <Badge label={getErrorMessage()} type={errorType ?? 'warning'}/>;

    return children;
};
```

## Benutzung des Patterns

```typescript jsx
import {type QueryBoundaryFnResult, useQueryBoundaryFn, QueryBoundary} from '@ui/QueryBoundary';

const MyComponent = () => {
    const queryResult: QueryBoundaryFnResult<string> = useQueryBoundaryFn<string>({queryKey: ["MyKeyForTheQuery"], queryFn: myApiFunctionToReadAString, ...})

    return <QueryBoundary queryResult={queryResult}>
        <div>Irgendwelche Komponenten, die nur im Erfolgsfall angezeigt werden soll</div>
    </QueryBoundary>
}
```