import * as React from 'react';
import { Badge } from '@ui/Badge/Badge.tsx';
import { AnfragenGenehmigung } from '@aufgabenFormular/formwrapper/AnfragenGenehmigung/AnfragenGenehmigung.tsx';

const formComponents: Record<string, React.FC<{ taskId: string }>> = {
  Test: AnfragenGenehmigung,
};

type Props = {
  formularreferenz?: string;
  taskId: string;
};

export const FormWrapper: React.FC<Props> = ({ formularreferenz, taskId }) => {
  const FormComponent = formularreferenz
    ? formComponents[formularreferenz]
    : undefined;

  if (!FormComponent) {
    return <Badge label='Kein passendes Formular gefunden' type='danger' />;
  }
  return <FormComponent taskId={taskId} />;
};
