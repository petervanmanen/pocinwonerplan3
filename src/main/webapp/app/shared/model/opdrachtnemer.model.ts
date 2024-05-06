import { IFunctie } from 'app/shared/model/functie.model';

export interface IOpdrachtnemer {
  id?: number;
  clustercode?: string | null;
  clustercodeomschrijving?: string | null;
  naam?: string | null;
  nummer?: string | null;
  omschrijving?: string | null;
  uitgevoerddoorFunctie?: IFunctie | null;
}

export const defaultValue: Readonly<IOpdrachtnemer> = {};
