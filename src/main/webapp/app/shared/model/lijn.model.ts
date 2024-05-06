import { ILijnengroep } from 'app/shared/model/lijnengroep.model';

export interface ILijn {
  id?: number;
  lijn?: string | null;
  omvatLijnengroep?: ILijnengroep | null;
}

export const defaultValue: Readonly<ILijn> = {};
