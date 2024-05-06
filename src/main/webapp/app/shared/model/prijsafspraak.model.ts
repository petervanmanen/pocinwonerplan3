import dayjs from 'dayjs';

export interface IPrijsafspraak {
  id?: number;
  datumeinde?: dayjs.Dayjs | null;
  datumstart?: dayjs.Dayjs | null;
  titel?: string | null;
}

export const defaultValue: Readonly<IPrijsafspraak> = {};
