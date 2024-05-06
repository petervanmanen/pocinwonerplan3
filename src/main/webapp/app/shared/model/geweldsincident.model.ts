import dayjs from 'dayjs';

export interface IGeweldsincident {
  id?: number;
  datum?: dayjs.Dayjs | null;
  omschrijving?: string | null;
  type?: string | null;
}

export const defaultValue: Readonly<IGeweldsincident> = {};
