import { render } from '@testing-library/react';
import { QueryBoundary } from './QueryBoundary.tsx';
import type { QueryBoundaryFnResult } from '@ui/QueryBoundary/query/useQueryBoundaryFn.ts';
import { Badge, type BadgeType } from '@ui/Badge/Badge.tsx';
import { buildUseQueryBoundaryFnResult } from '@ui/QueryBoundary/query/__mocks__/useQueryBoundaryFn.functions.ts';
import { LoadingSpin } from '@ui/LoadingSpin/LoadingSpin.tsx';

jest.mock('@ui/Badge/Badge.tsx');
jest.mock('@ui/LoadingSpin/LoadingSpin.tsx');

describe('QueryBoundary', () => {
  beforeEach(() => jest.clearAllMocks());

  const fetchingQueryResult: QueryBoundaryFnResult<string> =
    buildUseQueryBoundaryFnResult({ type: 'fetching' });
  const errorQueryResult: QueryBoundaryFnResult<string> =
    buildUseQueryBoundaryFnResult({
      type: 'error',
      errorMessage:
        'Ich wäre eine Fehlermeldung; sie sollte aber nur im Fehlerfall zu sehen sein',
    });
  const successQueryResult: QueryBoundaryFnResult<string> =
    buildUseQueryBoundaryFnResult({
      type: 'success',
    });

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
      const textWithinChild = 'Ich sollte nur im Erfolgsfall zu sehen sein';

      const { queryByText } = render(
        <QueryBoundary queryResult={queryResult} errorType={badgeType}>
          <div>{textWithinChild}</div>
        </QueryBoundary>,
      );

      if (queryResult.isFetching) {
        expect(queryByText(textWithinChild)).toBeNull();
        expect(jest.mocked(LoadingSpin)).toHaveBeenCalled();
        expect(jest.mocked(Badge)).not.toHaveBeenCalled();
      }

      if (queryResult.isError) {
        expect(queryByText(textWithinChild)).toBeNull();
        expect(jest.mocked(LoadingSpin)).not.toHaveBeenCalled();
        if (badgeType)
          expect(jest.mocked(Badge)).toHaveBeenCalledWith(
            {
              label: queryResult.getErrorMessage(),
              type: badgeType,
            },
            undefined,
          );
        else
          expect(jest.mocked(Badge)).toHaveBeenCalledWith(
            {
              label: queryResult.getErrorMessage(),
              type: 'warning',
            },
            undefined,
          );
      }

      if (queryResult.isSuccess) {
        expect(queryByText(textWithinChild)).not.toBeNull();
        expect(jest.mocked(LoadingSpin)).not.toHaveBeenCalled();
        expect(jest.mocked(Badge)).not.toHaveBeenCalled();
      }
    },
  );
});
