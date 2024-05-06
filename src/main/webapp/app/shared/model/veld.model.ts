import { IBelijning } from 'app/shared/model/belijning.model';
import { ISportpark } from 'app/shared/model/sportpark.model';

export interface IVeld {
  id?: number;
  heeftBelijnings?: IBelijning[] | null;
  heeftSportpark?: ISportpark | null;
}

export const defaultValue: Readonly<IVeld> = {};
