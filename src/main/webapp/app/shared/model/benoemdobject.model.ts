import dayjs from 'dayjs';

export interface IBenoemdobject {
  id?: number;
  datumbegingeldigheid?: dayjs.Dayjs | null;
  datumeindegeldigheid?: dayjs.Dayjs | null;
  geometriepunt?: string | null;
  geometrievlak?: string | null;
  identificatie?: string | null;
}

export const defaultValue: Readonly<IBenoemdobject> = {};
