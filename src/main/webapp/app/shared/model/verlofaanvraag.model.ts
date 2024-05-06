import dayjs from 'dayjs';

export interface IVerlofaanvraag {
  id?: number;
  datumstart?: dayjs.Dayjs | null;
  datumtot?: dayjs.Dayjs | null;
  soortverlof?: string | null;
}

export const defaultValue: Readonly<IVerlofaanvraag> = {};
