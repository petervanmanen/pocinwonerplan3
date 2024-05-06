import { IBedrijfsprocestype } from 'app/shared/model/bedrijfsprocestype.model';

export interface IDeelprocestype {
  id?: number;
  omschrijving?: string | null;
  isdeelvanBedrijfsprocestype?: IBedrijfsprocestype;
}

export const defaultValue: Readonly<IDeelprocestype> = {};
