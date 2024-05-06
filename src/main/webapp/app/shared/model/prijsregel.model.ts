import { IPrijsafspraak } from 'app/shared/model/prijsafspraak.model';

export interface IPrijsregel {
  id?: number;
  bedrag?: number | null;
  credit?: string | null;
  heeftPrijsafspraak?: IPrijsafspraak | null;
}

export const defaultValue: Readonly<IPrijsregel> = {};
