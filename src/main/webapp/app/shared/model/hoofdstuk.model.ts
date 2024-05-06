import { IPeriode } from 'app/shared/model/periode.model';

export interface IHoofdstuk {
  id?: number;
  naam?: string | null;
  nummer?: string | null;
  omschrijving?: string | null;
  binnenPeriodes?: IPeriode[] | null;
}

export const defaultValue: Readonly<IHoofdstuk> = {};
