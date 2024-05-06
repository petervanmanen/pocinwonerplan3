import dayjs from 'dayjs';

export interface IAanvraagvrijstelling {
  id?: number;
  buitenlandseschoollocatie?: string | null;
  datumaanvraag?: dayjs.Dayjs | null;
}

export const defaultValue: Readonly<IAanvraagvrijstelling> = {};
