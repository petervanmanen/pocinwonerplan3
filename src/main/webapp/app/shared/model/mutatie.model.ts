import { IHoofdrekening } from 'app/shared/model/hoofdrekening.model';
import { IKostenplaats } from 'app/shared/model/kostenplaats.model';

export interface IMutatie {
  id?: number;
  bedrag?: number | null;
  datum?: string | null;
  vanHoofdrekening?: IHoofdrekening | null;
  naarHoofdrekening?: IHoofdrekening | null;
  heeftbetrekkingopKostenplaats?: IKostenplaats | null;
}

export const defaultValue: Readonly<IMutatie> = {};
