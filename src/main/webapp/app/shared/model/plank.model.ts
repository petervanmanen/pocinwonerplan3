import { IKast } from 'app/shared/model/kast.model';

export interface IPlank {
  id?: number;
  planknummer?: string | null;
  heeftKast?: IKast | null;
}

export const defaultValue: Readonly<IPlank> = {};
