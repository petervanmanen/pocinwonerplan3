import dayjs from 'dayjs';

export interface IAdresbuitenland {
  id?: number;
  adresregelbuitenland1?: string | null;
  adresregelbuitenland2?: string | null;
  adresregelbuitenland3?: string | null;
  datumaanvangadresbuitenland?: dayjs.Dayjs | null;
  datuminschrijvinggemeente?: dayjs.Dayjs | null;
  datumvestigingnederland?: dayjs.Dayjs | null;
  gemeentevaninschrijving?: string | null;
  landadresbuitenland?: string | null;
  landwaarvandaaningeschreven?: string | null;
  omschrijvingvandeaangifteadreshouding?: string | null;
}

export const defaultValue: Readonly<IAdresbuitenland> = {};
