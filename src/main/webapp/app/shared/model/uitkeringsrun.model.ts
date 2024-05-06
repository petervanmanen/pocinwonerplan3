import dayjs from 'dayjs';

export interface IUitkeringsrun {
  id?: number;
  datumrun?: dayjs.Dayjs | null;
  frequentie?: string | null;
  perioderun?: string | null;
  soortrun?: string | null;
}

export const defaultValue: Readonly<IUitkeringsrun> = {};
