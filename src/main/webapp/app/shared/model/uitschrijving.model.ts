import dayjs from 'dayjs';
import { ILeerling } from 'app/shared/model/leerling.model';
import { ISchool } from 'app/shared/model/school.model';

export interface IUitschrijving {
  id?: number;
  datum?: dayjs.Dayjs | null;
  diplomabehaald?: boolean | null;
  heeftLeerling?: ILeerling | null;
  heeftSchool?: ISchool | null;
}

export const defaultValue: Readonly<IUitschrijving> = {
  diplomabehaald: false,
};
