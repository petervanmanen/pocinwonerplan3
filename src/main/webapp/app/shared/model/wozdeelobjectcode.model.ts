import dayjs from 'dayjs';

export interface IWozdeelobjectcode {
  id?: number;
  datumbegingeldigheiddeelojectcode?: dayjs.Dayjs | null;
  datumeindegeldigheiddeelobjectcode?: dayjs.Dayjs | null;
  deelobjectcode?: string | null;
  naamdeelobjectcode?: string | null;
}

export const defaultValue: Readonly<IWozdeelobjectcode> = {};
