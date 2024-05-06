import { IBedrijfsprocestype } from 'app/shared/model/bedrijfsprocestype.model';

export interface IProducttype {
  id?: number;
  omschrijving?: string | null;
  heeftBedrijfsprocestype?: IBedrijfsprocestype;
}

export const defaultValue: Readonly<IProducttype> = {};
