import { IRaadsstuk } from 'app/shared/model/raadsstuk.model';
import { IAgendapunt } from 'app/shared/model/agendapunt.model';

export interface IStemming {
  id?: number;
  resultaat?: string | null;
  stemmingstype?: string | null;
  betreftRaadsstuk?: IRaadsstuk | null;
  hoortbijAgendapunt?: IAgendapunt | null;
}

export const defaultValue: Readonly<IStemming> = {};
