import dayjs from 'dayjs';

export interface IHistorischpersoon {
  id?: number;
  beroep?: string | null;
  datumgeboorte?: dayjs.Dayjs | null;
  datumoverlijden?: dayjs.Dayjs | null;
  naam?: string | null;
  omschrijving?: string | null;
  publiektoegankelijk?: string | null;
  woondeop?: string | null;
}

export const defaultValue: Readonly<IHistorischpersoon> = {};
