import dayjs from 'dayjs';

export interface IAardfiliatie {
  id?: number;
  codeaardfiliatie?: string | null;
  datumbegingeldigheidaardfiliatie?: dayjs.Dayjs | null;
  datumeindegeldigheidaardfiliatie?: dayjs.Dayjs | null;
  naamaardfiliatie?: string | null;
}

export const defaultValue: Readonly<IAardfiliatie> = {};
