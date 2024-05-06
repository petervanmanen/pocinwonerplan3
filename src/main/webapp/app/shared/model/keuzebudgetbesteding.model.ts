import dayjs from 'dayjs';

export interface IKeuzebudgetbesteding {
  id?: number;
  bedrag?: number | null;
  datum?: dayjs.Dayjs | null;
}

export const defaultValue: Readonly<IKeuzebudgetbesteding> = {};
