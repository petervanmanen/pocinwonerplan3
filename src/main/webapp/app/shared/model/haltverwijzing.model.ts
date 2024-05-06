import dayjs from 'dayjs';

export interface IHaltverwijzing {
  id?: number;
  afdoening?: string | null;
  datummutatie?: dayjs.Dayjs | null;
  datumretour?: dayjs.Dayjs | null;
  memo?: string | null;
}

export const defaultValue: Readonly<IHaltverwijzing> = {};
