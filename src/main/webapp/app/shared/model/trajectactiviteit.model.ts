import dayjs from 'dayjs';

export interface ITrajectactiviteit {
  id?: number;
  contract?: string | null;
  crediteur?: string | null;
  datumbegin?: dayjs.Dayjs | null;
  datumeinde?: dayjs.Dayjs | null;
  datumstatus?: dayjs.Dayjs | null;
  kinderopvang?: string | null;
  status?: string | null;
  succesvol?: string | null;
}

export const defaultValue: Readonly<ITrajectactiviteit> = {};
