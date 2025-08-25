import axios from 'axios';

export async function uebernehmen(
  aufgabenId: string,
  userId: string,
): Promise<void> {
  await axios.put(`/api/aufgabe/${aufgabenId}/uebernehmen`, {
    userId: userId,
  });
}

export async function abgeben(aufgabenId: string): Promise<void> {
  await axios.put(`/api/aufgabe/${aufgabenId}/abgeben`);
}

export async function abschliessenMitVariablen(
  aufgabenId: string,
  variablen: Record<string, unknown> = {},
): Promise<void> {
  await axios.put(`/api/aufgabe/${aufgabenId}/abschliessenMitVariablen`, {
    variables: variablen,
  });
}
