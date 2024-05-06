import { IMedewerker } from 'app/shared/model/medewerker.model';
import { IZaak } from 'app/shared/model/zaak.model';

export interface IBetrokkene {
  id?: number;
  adresbinnenland?: string | null;
  adresbuitenland?: string | null;
  identificatie?: string | null;
  naam?: string | null;
  rol?: string | null;
  isMedewerker?: IMedewerker | null;
  oefentuitZaaks?: IZaak[];
}

export const defaultValue: Readonly<IBetrokkene> = {};
