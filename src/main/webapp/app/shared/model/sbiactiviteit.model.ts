import dayjs from 'dayjs';

export interface ISbiactiviteit {
  id?: number;
  datumeindesbiactiviteit?: dayjs.Dayjs | null;
  datumingangsbiactiviteit?: dayjs.Dayjs | null;
  hoofdniveau?: string | null;
  hoofdniveauomschrijving?: string | null;
  naamactiviteit?: string | null;
  sbicode?: string | null;
  sbigroep?: string | null;
  sbigroepomschrijving?: string | null;
}

export const defaultValue: Readonly<ISbiactiviteit> = {};
