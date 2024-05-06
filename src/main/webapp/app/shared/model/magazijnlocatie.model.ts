import { IStelling } from 'app/shared/model/stelling.model';

export interface IMagazijnlocatie {
  id?: number;
  key?: string | null;
  vaknummer?: string | null;
  volgletter?: string | null;
  heeftStelling?: IStelling | null;
}

export const defaultValue: Readonly<IMagazijnlocatie> = {};
