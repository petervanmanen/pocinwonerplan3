import dayjs from 'dayjs';

export interface IBudgetuitputting {
  id?: number;
  datum?: dayjs.Dayjs | null;
  uitgenutbedrag?: number | null;
}

export const defaultValue: Readonly<IBudgetuitputting> = {};
