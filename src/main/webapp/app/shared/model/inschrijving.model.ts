import dayjs from 'dayjs';
import { ISchool } from 'app/shared/model/school.model';
import { IAanbesteding } from 'app/shared/model/aanbesteding.model';
import { ILeerling } from 'app/shared/model/leerling.model';
import { ILeverancier } from 'app/shared/model/leverancier.model';

export interface IInschrijving {
  id?: number;
  datum?: dayjs.Dayjs | null;
  heeftSchool?: ISchool | null;
  betreftAanbesteding?: IAanbesteding | null;
  heeftLeerling?: ILeerling | null;
  heeftLeverancier?: ILeverancier | null;
}

export const defaultValue: Readonly<IInschrijving> = {};
