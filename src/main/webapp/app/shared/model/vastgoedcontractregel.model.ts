import dayjs from 'dayjs';

export interface IVastgoedcontractregel {
  id?: number;
  bedrag?: string | null;
  datumeinde?: dayjs.Dayjs | null;
  datumstart?: dayjs.Dayjs | null;
  frequentie?: string | null;
  identificatie?: string | null;
  omschrijving?: string | null;
  status?: string | null;
  type?: string | null;
}

export const defaultValue: Readonly<IVastgoedcontractregel> = {};
