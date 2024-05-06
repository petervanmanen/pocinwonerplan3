import dayjs from 'dayjs';

export interface IStartkwalificatie {
  id?: number;
  datumbehaald?: dayjs.Dayjs | null;
}

export const defaultValue: Readonly<IStartkwalificatie> = {};
