import dayjs from 'dayjs';

export interface IWozwaarde {
  id?: number;
  datumpeilingtoestand?: dayjs.Dayjs | null;
  datumwaardepeiling?: dayjs.Dayjs | null;
  statusbeschikking?: string | null;
  vastgesteldewaarde?: string | null;
}

export const defaultValue: Readonly<IWozwaarde> = {};
