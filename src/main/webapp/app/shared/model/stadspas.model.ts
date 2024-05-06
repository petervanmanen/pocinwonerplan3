import dayjs from 'dayjs';

export interface IStadspas {
  id?: number;
  datumeinde?: dayjs.Dayjs | null;
  datumstart?: dayjs.Dayjs | null;
}

export const defaultValue: Readonly<IStadspas> = {};
