import dayjs from 'dayjs';

export interface IStrooidag {
  id?: number;
  datum?: dayjs.Dayjs | null;
  maximumtemperatuur?: string | null;
  minimumtemperatuur?: string | null;
  tijdmaximumtemperatuur?: string | null;
  tijdminimumtemperatuur?: string | null;
}

export const defaultValue: Readonly<IStrooidag> = {};
