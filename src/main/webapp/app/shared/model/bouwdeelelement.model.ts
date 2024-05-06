import { IBouwdeel } from 'app/shared/model/bouwdeel.model';
import { IWerkbon } from 'app/shared/model/werkbon.model';

export interface IBouwdeelelement {
  id?: number;
  code?: string | null;
  omschrijving?: string | null;
  bestaatuitBouwdeel?: IBouwdeel | null;
  betreftWerkbons?: IWerkbon[] | null;
}

export const defaultValue: Readonly<IBouwdeelelement> = {};
