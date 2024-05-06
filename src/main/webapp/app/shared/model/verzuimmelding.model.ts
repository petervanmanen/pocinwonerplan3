import dayjs from 'dayjs';
import { ISchool } from 'app/shared/model/school.model';
import { ILeerling } from 'app/shared/model/leerling.model';

export interface IVerzuimmelding {
  id?: number;
  datumeinde?: dayjs.Dayjs | null;
  datumstart?: dayjs.Dayjs | null;
  voorstelschool?: string | null;
  heeftSchool?: ISchool | null;
  heeftLeerling?: ILeerling | null;
}

export const defaultValue: Readonly<IVerzuimmelding> = {};
