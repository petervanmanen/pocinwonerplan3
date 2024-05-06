import { IRaadsstuk } from 'app/shared/model/raadsstuk.model';
import { IRaadscommissie } from 'app/shared/model/raadscommissie.model';

export interface IVergadering {
  id?: number;
  eindtijd?: string | null;
  locatie?: string | null;
  starttijd?: string | null;
  titel?: string | null;
  heeftverslagRaadsstuk?: IRaadsstuk | null;
  heeftRaadscommissie?: IRaadscommissie | null;
  wordtbehandeldinRaadsstuks?: IRaadsstuk[] | null;
}

export const defaultValue: Readonly<IVergadering> = {};
