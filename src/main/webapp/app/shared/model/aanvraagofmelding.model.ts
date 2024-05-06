import dayjs from 'dayjs';

export interface IAanvraagofmelding {
  id?: number;
  datum?: dayjs.Dayjs | null;
  opmerkingen?: string | null;
  reden?: string | null;
  soortverzuimofaanvraag?: string | null;
}

export const defaultValue: Readonly<IAanvraagofmelding> = {};
