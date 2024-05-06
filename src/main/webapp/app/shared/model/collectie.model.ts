import { IMuseumobject } from 'app/shared/model/museumobject.model';

export interface ICollectie {
  id?: number;
  naam?: string | null;
  omschrijving?: string | null;
  bevatMuseumobjects?: IMuseumobject[] | null;
}

export const defaultValue: Readonly<ICollectie> = {};
