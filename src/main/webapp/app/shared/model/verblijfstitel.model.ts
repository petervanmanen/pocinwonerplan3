import dayjs from 'dayjs';

export interface IVerblijfstitel {
  id?: number;
  aanduidingverblijfstitel?: string | null;
  datumbegin?: dayjs.Dayjs | null;
  datumeinde?: dayjs.Dayjs | null;
  datumopname?: dayjs.Dayjs | null;
  datumbegingeldigheidverblijfstitel?: dayjs.Dayjs | null;
  verblijfstitelcode?: string | null;
}

export const defaultValue: Readonly<IVerblijfstitel> = {};
