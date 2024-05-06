import { IWerknemer } from 'app/shared/model/werknemer.model';

export interface IRol {
  id?: number;
  naam?: string | null;
  omschrijving?: string | null;
  heeftWerknemers?: IWerknemer[] | null;
}

export const defaultValue: Readonly<IRol> = {};
