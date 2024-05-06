import dayjs from 'dayjs';

export interface IKpbetrokkenbij {
  id?: number;
  datumbegingeldigheid?: dayjs.Dayjs | null;
  datumeindegeldigheid?: dayjs.Dayjs | null;
}

export const defaultValue: Readonly<IKpbetrokkenbij> = {};
