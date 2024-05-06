import dayjs from 'dayjs';
import { IMuseumobject } from 'app/shared/model/museumobject.model';

export interface IIncident {
  id?: number;
  datum?: dayjs.Dayjs | null;
  locatie?: string | null;
  naam?: string | null;
  omschrijving?: string | null;
  betreftMuseumobjects?: IMuseumobject[] | null;
}

export const defaultValue: Readonly<IIncident> = {};
