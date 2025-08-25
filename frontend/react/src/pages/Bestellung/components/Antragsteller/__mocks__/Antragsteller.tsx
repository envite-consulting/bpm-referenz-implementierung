export const Antragsteller = jest.fn(
  ({ onSelectId }: { onSelectId: (id: string) => void }) => (
    <div
      data-testid='antragsteller-mock'
      onClick={() => onSelectId('antragsteller-mock-id')}
    >
      Antragsteller Mock
    </div>
  ),
);
