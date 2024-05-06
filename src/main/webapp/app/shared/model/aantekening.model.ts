import dayjs from 'dayjs';
import { ITenaamstelling } from 'app/shared/model/tenaamstelling.model';

export interface IAantekening {
  id?: number;
  aard?: string | null;
  begrenzing?: string | null;
  betreftgedeeltevanperceel?: boolean | null;
  datumeinde?: dayjs.Dayjs | null;
  datumeinderecht?: dayjs.Dayjs | null;
  identificatie?: string | null;
  omschrijving?: string | null;
  emptyTenaamstelling?: ITenaamstelling | null;
}

export const defaultValue: Readonly<IAantekening> = {
  betreftgedeeltevanperceel: false,
};
