import dayjs from 'dayjs';
import { IRaadscommissie } from 'app/shared/model/raadscommissie.model';

export interface IRaadslid {
  id?: number;
  achternaam?: string | null;
  datumaanstelling?: dayjs.Dayjs | null;
  datumuittreding?: dayjs.Dayjs | null;
  fractie?: string | null;
  titel?: string | null;
  voornaam?: string | null;
  islidvanRaadscommissies?: IRaadscommissie[] | null;
}

export const defaultValue: Readonly<IRaadslid> = {};
