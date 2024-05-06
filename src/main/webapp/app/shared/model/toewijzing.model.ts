import dayjs from 'dayjs';
import { ILeverancier } from 'app/shared/model/leverancier.model';
import { IBeschikking } from 'app/shared/model/beschikking.model';

export interface IToewijzing {
  id?: number;
  code?: string | null;
  commentaar?: string | null;
  datumaanschaf?: dayjs.Dayjs | null;
  datumeindetoewijzing?: dayjs.Dayjs | null;
  datumstarttoewijzing?: dayjs.Dayjs | null;
  datumtoewijzing?: dayjs.Dayjs | null;
  eenheid?: string | null;
  frequentie?: string | null;
  omvang?: string | null;
  redenwijziging?: string | null;
  toewijzingnummer?: string | null;
  wet?: string | null;
  levertvoorzieningLeverancier?: ILeverancier | null;
  toewijzingBeschikking?: IBeschikking | null;
}

export const defaultValue: Readonly<IToewijzing> = {};
