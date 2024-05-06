import { IDepot } from 'app/shared/model/depot.model';

export interface IStelling {
  id?: number;
  inhoud?: string | null;
  stellingcode?: string | null;
  heeftDepot?: IDepot | null;
}

export const defaultValue: Readonly<IStelling> = {};
