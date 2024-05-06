import dayjs from 'dayjs';

export interface IBevinding {
  id?: number;
  aangemaaktdoor?: string | null;
  activiteit?: string | null;
  controleelement?: string | null;
  controleniveau?: string | null;
  datumaanmaak?: dayjs.Dayjs | null;
  datummutatie?: dayjs.Dayjs | null;
  diepte?: string | null;
  fase?: string | null;
  gemuteerddoor?: string | null;
  resultaat?: string | null;
  risico?: string | null;
}

export const defaultValue: Readonly<IBevinding> = {};
