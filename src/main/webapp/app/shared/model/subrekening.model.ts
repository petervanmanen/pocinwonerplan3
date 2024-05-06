import { IHoofdrekening } from 'app/shared/model/hoofdrekening.model';
import { IKostenplaats } from 'app/shared/model/kostenplaats.model';

export interface ISubrekening {
  id?: number;
  naam?: string | null;
  nummer?: string | null;
  omschrijving?: string | null;
  heeftHoofdrekening?: IHoofdrekening | null;
  heeftKostenplaats?: IKostenplaats | null;
}

export const defaultValue: Readonly<ISubrekening> = {};
