import dayjs from 'dayjs';

export interface IVthmelding {
  id?: number;
  activiteit?: string | null;
  beoordeling?: string | null;
  datumseponering?: dayjs.Dayjs | null;
  datumtijdtot?: string | null;
  geseponeerd?: string | null;
  locatie?: string | null;
  organisatieonderdeel?: string | null;
  overtredingscode?: string | null;
  overtredingsgroep?: string | null;
  referentienummer?: string | null;
  resultaat?: string | null;
  soortvthmelding?: string | null;
  status?: string | null;
  straatnaam?: string | null;
  taaktype?: string | null;
  zaaknummer?: string | null;
}

export const defaultValue: Readonly<IVthmelding> = {};
