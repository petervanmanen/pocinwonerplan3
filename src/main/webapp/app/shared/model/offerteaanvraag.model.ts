import dayjs from 'dayjs';
import { IAanbesteding } from 'app/shared/model/aanbesteding.model';
import { ILeverancier } from 'app/shared/model/leverancier.model';

export interface IOfferteaanvraag {
  id?: number;
  datumaanvraag?: dayjs.Dayjs | null;
  datumsluiting?: string | null;
  naam?: string | null;
  omschrijving?: string | null;
  betreftAanbesteding?: IAanbesteding | null;
  gerichtaanLeverancier?: ILeverancier | null;
}

export const defaultValue: Readonly<IOfferteaanvraag> = {};
