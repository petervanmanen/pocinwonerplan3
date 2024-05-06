import dayjs from 'dayjs';

export interface IInzet {
  id?: number;
  datumbegin?: dayjs.Dayjs | null;
  datumeinde?: dayjs.Dayjs | null;
  percentage?: string | null;
  uren?: string | null;
}

export const defaultValue: Readonly<IInzet> = {};
