import dayjs from 'dayjs';
import { ISchool } from 'app/shared/model/school.model';
import { ILeerling } from 'app/shared/model/leerling.model';

export interface IVrijstelling {
  id?: number;
  aanvraagtoegekend?: boolean | null;
  buitenlandseschoollocatie?: string | null;
  datumeinde?: dayjs.Dayjs | null;
  datumstart?: dayjs.Dayjs | null;
  verzuimsoort?: string | null;
  heeftSchool?: ISchool | null;
  heeftLeerling?: ILeerling | null;
}

export const defaultValue: Readonly<IVrijstelling> = {
  aanvraagtoegekend: false,
};
