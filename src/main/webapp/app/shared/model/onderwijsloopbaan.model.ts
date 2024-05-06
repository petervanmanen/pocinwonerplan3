import { ILeerling } from 'app/shared/model/leerling.model';
import { ISchool } from 'app/shared/model/school.model';

export interface IOnderwijsloopbaan {
  id?: number;
  heeftLeerling?: ILeerling | null;
  kentSchools?: ISchool[] | null;
}

export const defaultValue: Readonly<IOnderwijsloopbaan> = {};
