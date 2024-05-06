import dayjs from 'dayjs';

export interface IBeschermdestatus {
  id?: number;
  bronnen?: string | null;
  complex?: string | null;
  datuminschrijvingregister?: dayjs.Dayjs | null;
  gemeentelijkmonumentcode?: string | null;
  gezichtscode?: string | null;
  naam?: string | null;
  omschrijving?: string | null;
  opmerkingen?: string | null;
  rijksmonumentcode?: string | null;
  type?: string | null;
}

export const defaultValue: Readonly<IBeschermdestatus> = {};
