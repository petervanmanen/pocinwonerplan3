import dayjs from 'dayjs';

export interface ICollegelid {
  id?: number;
  achternaam?: string | null;
  datumaanstelling?: dayjs.Dayjs | null;
  datumuittreding?: dayjs.Dayjs | null;
  fractie?: string | null;
  portefeuille?: string | null;
  titel?: string | null;
  voornaam?: string | null;
}

export const defaultValue: Readonly<ICollegelid> = {};
