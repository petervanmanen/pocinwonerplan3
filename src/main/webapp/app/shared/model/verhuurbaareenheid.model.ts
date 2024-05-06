import dayjs from 'dayjs';

export interface IVerhuurbaareenheid {
  id?: number;
  adres?: string | null;
  afmeting?: string | null;
  bezetting?: string | null;
  datumeinde?: dayjs.Dayjs | null;
  datumstart?: dayjs.Dayjs | null;
  datumwerkelijkbegin?: dayjs.Dayjs | null;
  datumwerkelijkeinde?: dayjs.Dayjs | null;
  huurprijs?: string | null;
  identificatie?: string | null;
  naam?: string | null;
  nettoomtrek?: string | null;
  nettooppervlak?: string | null;
  opmerkingen?: string | null;
  type?: string | null;
}

export const defaultValue: Readonly<IVerhuurbaareenheid> = {};
