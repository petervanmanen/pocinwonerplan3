import dayjs from 'dayjs';

export interface IMjopitem {
  id?: number;
  code?: string | null;
  datumeinde?: dayjs.Dayjs | null;
  datumopzeggingaanbieder?: dayjs.Dayjs | null;
  datumopzeggingontvanger?: dayjs.Dayjs | null;
  datumstart?: dayjs.Dayjs | null;
  kosten?: number | null;
  omschrijving?: string | null;
  opzegtermijnaanbieder?: string | null;
  opzegtermijnontvanger?: string | null;
}

export const defaultValue: Readonly<IMjopitem> = {};
