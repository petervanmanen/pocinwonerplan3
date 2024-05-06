import dayjs from 'dayjs';

export interface IOpenbareruimte {
  id?: number;
  datumbegingeldigheid?: dayjs.Dayjs | null;
  datumeinde?: dayjs.Dayjs | null;
  datumeindegeldigheid?: dayjs.Dayjs | null;
  datumingang?: dayjs.Dayjs | null;
  geconstateerd?: boolean | null;
  geometrie?: string | null;
  huisnummerrangeevenenonevennummers?: string | null;
  huisnummerrangeevennummers?: string | null;
  huisnummerrangeonevennummers?: string | null;
  identificatie?: string | null;
  inonderzoek?: boolean | null;
  labelnaam?: string | null;
  naamopenbareruimte?: string | null;
  status?: string | null;
  straatcode?: string | null;
  straatnaam?: string | null;
  typeopenbareruimte?: string | null;
  versie?: string | null;
  wegsegment?: string | null;
}

export const defaultValue: Readonly<IOpenbareruimte> = {
  geconstateerd: false,
  inonderzoek: false,
};
