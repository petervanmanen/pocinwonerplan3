import dayjs from 'dayjs';

export interface ILocatieonroerendezaak {
  id?: number;
  adrestype?: string | null;
  cultuurcodebebouwd?: string | null;
  datumbegingeldigheid?: dayjs.Dayjs | null;
  datumeindegeldigheid?: dayjs.Dayjs | null;
  geometrie?: string | null;
  locatieomschrijving?: string | null;
}

export const defaultValue: Readonly<ILocatieonroerendezaak> = {};
