import dayjs from 'dayjs';

export interface ISplitsingstekeningreferentie {
  id?: number;
  bronorganisatie?: string | null;
  datumcreatie?: dayjs.Dayjs | null;
  identificatietekening?: string | null;
  titel?: string | null;
}

export const defaultValue: Readonly<ISplitsingstekeningreferentie> = {};
