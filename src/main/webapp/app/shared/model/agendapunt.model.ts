import { IVergadering } from 'app/shared/model/vergadering.model';
import { IRaadsstuk } from 'app/shared/model/raadsstuk.model';

export interface IAgendapunt {
  id?: number;
  nummer?: string | null;
  omschrijving?: string | null;
  titel?: string | null;
  heeftVergadering?: IVergadering | null;
  behandeltRaadsstuks?: IRaadsstuk[] | null;
}

export const defaultValue: Readonly<IAgendapunt> = {};
