export const Fahrzeug = jest.fn(
  ({ onSelectId }: { onSelectId: (id: string) => void }) => (
    <div
      data-testid='fahrzeug-mock'
      onClick={() => onSelectId('fahrzeug-mock-id')}
    >
      Fahrzeug Mock
    </div>
  ),
);
