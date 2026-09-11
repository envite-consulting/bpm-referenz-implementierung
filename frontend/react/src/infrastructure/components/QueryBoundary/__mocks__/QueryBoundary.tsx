import { QueryBoundary as QueryBoundaryOriginal } from '../QueryBoundary';

type QueryResultWithOptionalFunctions = {
  isError?: boolean;
  isFetching?: boolean;
  isSuccess?: boolean;
  getErrorMessage?: () => string;
  getData?: () => never;
};

export const QueryBoundary: typeof QueryBoundaryOriginal = jest.fn((props) => {
  const queryResultWithOptionalFunctions =
    props.queryResult as QueryResultWithOptionalFunctions;
  const queryResult = {
    isError: queryResultWithOptionalFunctions.isError,
    isFetching: queryResultWithOptionalFunctions.isFetching,
    isSuccess: queryResultWithOptionalFunctions.isSuccess,
    errorMessage: queryResultWithOptionalFunctions.getErrorMessage?.(),
    data: queryResultWithOptionalFunctions.getData?.(),
  };
  return (
    <div data-testid='QueryBoundaryMock'>
      <div data-testid='queryResult'>{JSON.stringify(queryResult)}</div>
      <div data-testid='children'>{props.children}</div>
    </div>
  );
});
