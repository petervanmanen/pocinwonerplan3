import { IMedewerker } from 'app/shared/model/medewerker.model';
import { IAreaal } from 'app/shared/model/areaal.model';

export interface ISchouwronde {
  id?: number;
  voertuitMedewerker?: IMedewerker | null;
  binnenAreaals?: IAreaal[] | null;
}

export const defaultValue: Readonly<ISchouwronde> = {};
