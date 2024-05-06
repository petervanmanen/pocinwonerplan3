import dayjs from 'dayjs';

export interface IWozbelang {
  id?: number;
  datumbegingeldigheid?: dayjs.Dayjs | null;
  datumeindegeldigheid?: dayjs.Dayjs | null;
  eigenaargebruiker?: string | null;
}

export const defaultValue: Readonly<IWozbelang> = {};
