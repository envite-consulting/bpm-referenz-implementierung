export const AnfragenGenehmigung = jest.fn(({ taskId }) => (
  <p data-taskid={taskId} data-testid='anfragen-mock'></p>
));
