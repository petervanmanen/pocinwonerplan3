import { IHoofdrekening } from 'app/shared/model/hoofdrekening.model';
import { IKostenplaats } from 'app/shared/model/kostenplaats.model';

export interface IWerkorder {
  id?: number;
  code?: string | null;
  documentnummer?: string | null;
  naam?: string | null;
  omschrijving?: string | null;
  werkordertype?: string | null;
  heeftHoofdrekening?: IHoofdrekening | null;
  heeftKostenplaats?: IKostenplaats | null;
}

export const defaultValue: Readonly<IWerkorder> = {};
