import dayjs from 'dayjs';

export interface IAuteur {
  id?: number;
  datumgeboorte?: dayjs.Dayjs | null;
  datumoverlijden?: dayjs.Dayjs | null;
}

export const defaultValue: Readonly<IAuteur> = {};
