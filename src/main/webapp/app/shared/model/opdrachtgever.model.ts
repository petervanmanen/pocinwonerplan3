import { IFunctie } from 'app/shared/model/functie.model';

export interface IOpdrachtgever {
  id?: number;
  clustercode?: string | null;
  clusteromschrijving?: string | null;
  naam?: string | null;
  nummer?: string | null;
  omschrijving?: string | null;
  uitgevoerddoorFunctie?: IFunctie | null;
}

export const defaultValue: Readonly<IOpdrachtgever> = {};
