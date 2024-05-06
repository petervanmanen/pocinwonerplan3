import { IKostenplaats } from 'app/shared/model/kostenplaats.model';

export interface ISubsidiecomponent {
  id?: number;
  gereserveerdbedrag?: number | null;
  toegekendbedrag?: number | null;
  heeftKostenplaats?: IKostenplaats | null;
}

export const defaultValue: Readonly<ISubsidiecomponent> = {};
