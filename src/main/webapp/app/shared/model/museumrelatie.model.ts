import { IDoelgroep } from 'app/shared/model/doelgroep.model';
import { IMailing } from 'app/shared/model/mailing.model';

export interface IMuseumrelatie {
  id?: number;
  relatiesoort?: string | null;
  valtbinnenDoelgroeps?: IDoelgroep[] | null;
  versturenaanMailings?: IMailing[] | null;
}

export const defaultValue: Readonly<IMuseumrelatie> = {};
