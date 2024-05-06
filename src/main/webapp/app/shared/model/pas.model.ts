import { IMilieustraat } from 'app/shared/model/milieustraat.model';

export interface IPas {
  id?: number;
  adresaanduiding?: string | null;
  pasnummer?: string | null;
  geldigvoorMilieustraats?: IMilieustraat[] | null;
}

export const defaultValue: Readonly<IPas> = {};
