import dayjs from 'dayjs';

export interface IKponstaanuit {
  id?: number;
  datumbegingeldigheid?: dayjs.Dayjs | null;
  datumeindegeldigheid?: dayjs.Dayjs | null;
}

export const defaultValue: Readonly<IKponstaanuit> = {};
