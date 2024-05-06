import { IProgramma } from 'app/shared/model/programma.model';

export interface IProgrammasoort {
  id?: number;
  naam?: string | null;
  omschrijving?: string | null;
  voorProgrammas?: IProgramma[] | null;
}

export const defaultValue: Readonly<IProgrammasoort> = {};
