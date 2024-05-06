import dayjs from 'dayjs';

export interface IRedenverkrijgingnationaliteit {
  id?: number;
  datumaanvanggeldigheidverkrijging?: dayjs.Dayjs | null;
  datumeindegeldigheidverkrijging?: dayjs.Dayjs | null;
  omschrijvingverkrijging?: string | null;
  redennummerverkrijging?: string | null;
}

export const defaultValue: Readonly<IRedenverkrijgingnationaliteit> = {};
