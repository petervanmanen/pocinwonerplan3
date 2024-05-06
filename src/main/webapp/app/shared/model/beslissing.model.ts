import dayjs from 'dayjs';
import { ILeerling } from 'app/shared/model/leerling.model';
import { ILeerplichtambtenaar } from 'app/shared/model/leerplichtambtenaar.model';
import { ISchool } from 'app/shared/model/school.model';

export interface IBeslissing {
  id?: number;
  datum?: dayjs.Dayjs | null;
  opmerkingen?: string | null;
  reden?: string | null;
  betreftLeerling?: ILeerling | null;
  behandelaarLeerplichtambtenaar?: ILeerplichtambtenaar | null;
  betreftSchool?: ISchool | null;
}

export const defaultValue: Readonly<IBeslissing> = {};
