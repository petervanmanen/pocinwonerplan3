import dayjs from 'dayjs';

export interface IIndividueelkeuzebudget {
  id?: number;
  bedrag?: number | null;
  datumeinde?: dayjs.Dayjs | null;
  datumstart?: dayjs.Dayjs | null;
  datumtoekenning?: dayjs.Dayjs | null;
}

export const defaultValue: Readonly<IIndividueelkeuzebudget> = {};
