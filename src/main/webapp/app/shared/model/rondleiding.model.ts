import { ITentoonstelling } from 'app/shared/model/tentoonstelling.model';

export interface IRondleiding {
  id?: number;
  eindtijd?: string | null;
  naam?: string | null;
  omschrijving?: string | null;
  starttijd?: string | null;
  voorTentoonstelling?: ITentoonstelling | null;
}

export const defaultValue: Readonly<IRondleiding> = {};
