import { IRegeltekst } from 'app/shared/model/regeltekst.model';

export interface IIdealisatie {
  id?: number;
  naam?: string | null;
  omschrijving?: string | null;
  heeftidealisatieRegelteksts?: IRegeltekst[] | null;
}

export const defaultValue: Readonly<IIdealisatie> = {};
