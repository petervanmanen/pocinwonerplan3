import dayjs from 'dayjs';
import { IMedewerker } from 'app/shared/model/medewerker.model';
import { IApplicatie } from 'app/shared/model/applicatie.model';

export interface INotitie {
  id?: number;
  datum?: dayjs.Dayjs | null;
  inhoud?: string | null;
  auteurMedewerker?: IMedewerker | null;
  heeftnotitiesApplicatie?: IApplicatie | null;
}

export const defaultValue: Readonly<INotitie> = {};
