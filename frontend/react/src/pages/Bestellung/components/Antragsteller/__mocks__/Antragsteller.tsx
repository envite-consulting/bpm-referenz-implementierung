export const Antragsteller = jest.fn(
  ({ onSelectId }: { onSelectId: (id: string) => void }) => (
    <div data-testid='antragsteller-mock' onClick={() => onSelectId('mock-id')}>
      Antragsteller Mock
    </div>
  ),
);
