import { IStelling } from 'app/shared/model/stelling.model';

export interface IKast {
  id?: number;
  kastnummer?: string | null;
  heeftStelling?: IStelling | null;
}

export const defaultValue: Readonly<IKast> = {};
