import { IVastgoedobject } from 'app/shared/model/vastgoedobject.model';
import { IWerkbon } from 'app/shared/model/werkbon.model';

export interface IBouwdeel {
  id?: number;
  code?: string | null;
  omschrijving?: string | null;
  bestaatuitVastgoedobject?: IVastgoedobject | null;
  betreftWerkbons?: IWerkbon[] | null;
}

export const defaultValue: Readonly<IBouwdeel> = {};
