import { IRegeltekst } from 'app/shared/model/regeltekst.model';

export interface IThema {
  id?: number;
  naam?: string | null;
  omschrijving?: string | null;
  subthemaThema2?: IThema | null;
  heeftthemaRegelteksts?: IRegeltekst[] | null;
}

export const defaultValue: Readonly<IThema> = {};
