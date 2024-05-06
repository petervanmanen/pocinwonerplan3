import { IKostenplaats } from 'app/shared/model/kostenplaats.model';

export interface ITaakveld {
  id?: number;
  naam?: string | null;
  heeftKostenplaats?: IKostenplaats[];
}

export const defaultValue: Readonly<ITaakveld> = {};
