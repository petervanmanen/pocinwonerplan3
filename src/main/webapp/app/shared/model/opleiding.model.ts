import { IOnderwijsinstituut } from 'app/shared/model/onderwijsinstituut.model';

export interface IOpleiding {
  id?: number;
  instituut?: string | null;
  naam?: string | null;
  omschrijving?: string | null;
  prijs?: string | null;
  wordtgegevendoorOnderwijsinstituuts?: IOnderwijsinstituut[];
}

export const defaultValue: Readonly<IOpleiding> = {};
