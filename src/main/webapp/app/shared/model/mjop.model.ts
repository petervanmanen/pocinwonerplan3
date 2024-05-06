import dayjs from 'dayjs';

export interface IMjop {
  id?: number;
  datum?: dayjs.Dayjs | null;
  omschrijving?: string | null;
}

export const defaultValue: Readonly<IMjop> = {};
