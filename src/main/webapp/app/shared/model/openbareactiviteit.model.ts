import dayjs from 'dayjs';

export interface IOpenbareactiviteit {
  id?: number;
  datumeinde?: dayjs.Dayjs | null;
  datumstart?: dayjs.Dayjs | null;
  evenmentnaam?: string | null;
  locatieomschrijving?: string | null;
  status?: string | null;
}

export const defaultValue: Readonly<IOpenbareactiviteit> = {};
