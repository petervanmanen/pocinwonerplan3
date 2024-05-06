import dayjs from 'dayjs';

export interface IEcomponent {
  id?: number;
  bedrag?: string | null;
  datumbeginbetrekkingop?: dayjs.Dayjs | null;
  datumeindebetrekkingop?: dayjs.Dayjs | null;
  debetcredit?: string | null;
  groep?: string | null;
  groepcode?: string | null;
  grootboekcode?: string | null;
  grootboekomschrijving?: string | null;
  kostenplaats?: string | null;
  omschrijving?: string | null;
  rekeningnummer?: string | null;
  toelichting?: string | null;
}

export const defaultValue: Readonly<IEcomponent> = {};
