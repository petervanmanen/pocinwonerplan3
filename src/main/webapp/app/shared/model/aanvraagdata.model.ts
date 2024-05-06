import { IFormuliersoortveld } from 'app/shared/model/formuliersoortveld.model';

export interface IAanvraagdata {
  id?: number;
  data?: string | null;
  veld?: string | null;
  isconformFormuliersoortveld?: IFormuliersoortveld | null;
}

export const defaultValue: Readonly<IAanvraagdata> = {};
