import dayjs from 'dayjs';

export interface IMaatschappelijkeactiviteit {
  id?: number;
  adresbinnenland?: string | null;
  adrescorrespondentie?: string | null;
  datumaanvang?: dayjs.Dayjs | null;
  datumeindegeldig?: dayjs.Dayjs | null;
  datumfaillisement?: dayjs.Dayjs | null;
  indicatieeconomischactief?: string | null;
  kvknummer?: string | null;
  rechtsvorm?: string | null;
  rsin?: string | null;
  statutairenaam?: string | null;
  telefoonnummer?: string | null;
  url?: string | null;
}

export const defaultValue: Readonly<IMaatschappelijkeactiviteit> = {};
