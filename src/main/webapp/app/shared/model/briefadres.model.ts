import dayjs from 'dayjs';
import { INummeraanduiding } from 'app/shared/model/nummeraanduiding.model';

export interface IBriefadres {
  id?: number;
  adresfunctie?: string | null;
  datumaanvang?: dayjs.Dayjs | null;
  datumeinde?: dayjs.Dayjs | null;
  omschrijvingaangifte?: string | null;
  emptyNummeraanduiding?: INummeraanduiding | null;
}

export const defaultValue: Readonly<IBriefadres> = {};
