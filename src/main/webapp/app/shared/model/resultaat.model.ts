import { IResultaatsoort } from 'app/shared/model/resultaatsoort.model';

export interface IResultaat {
  id?: number;
  datum?: string | null;
  naam?: string | null;
  omschrijving?: string | null;
  soortresultaatResultaatsoort?: IResultaatsoort | null;
}

export const defaultValue: Readonly<IResultaat> = {};
