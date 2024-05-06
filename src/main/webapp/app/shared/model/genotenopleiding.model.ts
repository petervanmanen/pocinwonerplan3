import dayjs from 'dayjs';

export interface IGenotenopleiding {
  id?: number;
  datumeinde?: dayjs.Dayjs | null;
  datumstart?: dayjs.Dayjs | null;
  datumtoewijzing?: dayjs.Dayjs | null;
  prijs?: number | null;
  verrekenen?: string | null;
}

export const defaultValue: Readonly<IGenotenopleiding> = {};
