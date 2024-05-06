import dayjs from 'dayjs';

export interface ISubsidieprogramma {
  id?: number;
  datumeinde?: dayjs.Dayjs | null;
  datumstart?: dayjs.Dayjs | null;
  naam?: string | null;
  omschrijving?: string | null;
  programmabegroting?: number | null;
}

export const defaultValue: Readonly<ISubsidieprogramma> = {};
