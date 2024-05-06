import { IVoorzieningsoort } from 'app/shared/model/voorzieningsoort.model';

export interface IVoorziening {
  id?: number;
  aantalbeschikbaar?: string | null;
  naam?: string | null;
  omschrijving?: string | null;
  valtbinnenVoorzieningsoort?: IVoorzieningsoort | null;
}

export const defaultValue: Readonly<IVoorziening> = {};
