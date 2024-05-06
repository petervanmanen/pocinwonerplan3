import { IVuilniswagen } from 'app/shared/model/vuilniswagen.model';

export interface IContainertype {
  id?: number;
  naam?: string | null;
  omschrijving?: string | null;
  geschiktvoorVuilniswagens?: IVuilniswagen[];
}

export const defaultValue: Readonly<IContainertype> = {};
