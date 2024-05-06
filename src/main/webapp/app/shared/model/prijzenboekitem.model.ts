import dayjs from 'dayjs';

export interface IPrijzenboekitem {
  id?: number;
  datumeindegeldigheid?: dayjs.Dayjs | null;
  datumstart?: dayjs.Dayjs | null;
  prijs?: number | null;
  verrichting?: string | null;
}

export const defaultValue: Readonly<IPrijzenboekitem> = {};
