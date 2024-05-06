import dayjs from 'dayjs';
import { IWerknemer } from 'app/shared/model/werknemer.model';

export interface IBeoordeling {
  id?: number;
  datum?: dayjs.Dayjs | null;
  omschrijving?: string | null;
  oordeel?: string | null;
  beoordeeltdoorWerknemer?: IWerknemer | null;
  beoordelingvanWerknemer?: IWerknemer | null;
}

export const defaultValue: Readonly<IBeoordeling> = {};
