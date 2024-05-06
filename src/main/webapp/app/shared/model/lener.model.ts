import { IBruikleen } from 'app/shared/model/bruikleen.model';

export interface ILener {
  id?: number;
  opmerkingen?: string | null;
  isBruikleens?: IBruikleen[];
}

export const defaultValue: Readonly<ILener> = {};
