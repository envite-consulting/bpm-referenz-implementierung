import { render } from '@testing-library/react';
import { QueryBoundary } from './QueryBoundary.tsx';
import type { QueryBoundaryFnResult } from '@ui/QueryBoundary/query/useQueryBoundaryFn.ts';
import type { BadgeType } from '@ui/Badge/Badge.tsx';

describe('QueryBoundary', () => {
  const defaultQueryResult: QueryBoundaryFnResult<string> = {
    getErrorMessage: () =>
      'Ich wäre eine Fehlermeldung; sie sollte aber nur im Fehlerfall zu sehen sein',
    isFetching: false,
    isSuccess: false,
    isError: false,
    getData: () =>
      'Das wären die Daten, die aber maximal im Kindelement zu sehen sein sollten',
  };
  const fetchingQueryResult: QueryBoundaryFnResult<string> = {
    ...defaultQueryResult,
    isFetching: true,
  };
  const errorQueryResult = {
    ...defaultQueryResult,
    isError: true,
  };
  const successQueryResult = {
    ...defaultQueryResult,
    isSuccess: true,
  };

  it.each([
    [fetchingQueryResult, 'success' as BadgeType],
    [errorQueryResult, 'success' as BadgeType],
    [successQueryResult, 'success' as BadgeType],
    [fetchingQueryResult, 'info' as BadgeType],
    [errorQueryResult, 'info' as BadgeType],
    [successQueryResult, 'info' as BadgeType],
    [fetchingQueryResult, 'danger' as BadgeType],
    [errorQueryResult, 'danger' as BadgeType],
    [successQueryResult, 'danger' as BadgeType],
    [fetchingQueryResult, 'warning' as BadgeType],
    [errorQueryResult, 'warning' as BadgeType],
    [successQueryResult, 'warning' as BadgeType],
    [fetchingQueryResult, undefined],
    [errorQueryResult, undefined],
    [successQueryResult, undefined],
  ])(
    'renders correctly: %o (Badge: %s)',
    (queryResult, badgeType?: BadgeType) => {
      const { asFragment } = render(
        <QueryBoundary queryResult={queryResult} errorType={badgeType}>
          <div>Ich sollte nur im Erfolgsfall zu sehen sein</div>
          <div>{queryResult.getData()}</div>
        </QueryBoundary>,
      );
      expect(asFragment()).toMatchSnapshot();
    },
  );
});
