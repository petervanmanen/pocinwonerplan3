import { ISchool } from 'app/shared/model/school.model';

export interface IOnderwijssoort {
  id?: number;
  omschrijving?: string | null;
  onderwijstype?: string | null;
  heeftSchools?: ISchool[];
}

export const defaultValue: Readonly<IOnderwijssoort> = {};
