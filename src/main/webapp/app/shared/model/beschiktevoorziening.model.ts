import dayjs from 'dayjs';

export interface IBeschiktevoorziening {
  id?: number;
  code?: string | null;
  datumeinde?: dayjs.Dayjs | null;
  datumeindeoorspronkelijk?: dayjs.Dayjs | null;
  datumstart?: dayjs.Dayjs | null;
  eenheid?: string | null;
  frequentie?: string | null;
  leveringsvorm?: string | null;
  omvang?: string | null;
  redeneinde?: string | null;
  status?: string | null;
  wet?: string | null;
}

export const defaultValue: Readonly<IBeschiktevoorziening> = {};
