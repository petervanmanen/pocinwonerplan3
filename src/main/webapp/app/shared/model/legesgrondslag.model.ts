import dayjs from 'dayjs';

export interface ILegesgrondslag {
  id?: number;
  aangemaaktdoor?: string | null;
  aantalopgegeven?: string | null;
  aantalvastgesteld?: string | null;
  automatisch?: string | null;
  datumaanmaak?: string | null;
  datummutatie?: dayjs.Dayjs | null;
  eenheid?: string | null;
  gemuteerddoor?: string | null;
  legesgrondslag?: string | null;
  omschrijving?: string | null;
}

export const defaultValue: Readonly<ILegesgrondslag> = {};
