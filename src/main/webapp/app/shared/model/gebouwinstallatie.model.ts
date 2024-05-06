import dayjs from 'dayjs';

export interface IGebouwinstallatie {
  id?: number;
  datumbegingeldigheidgebouwinstallatie?: dayjs.Dayjs | null;
  datumeindegeldigheidgebouwinstallatie?: dayjs.Dayjs | null;
  geometriegebouwinstallatie?: string | null;
  identificatiegebouwinstallatie?: string | null;
  lod0geometriegebouwinstallatie?: string | null;
  relatievehoogteligginggebouwinstallatie?: string | null;
  statusgebouwinstallatie?: string | null;
  typegebouwinstallatie?: string | null;
}

export const defaultValue: Readonly<IGebouwinstallatie> = {};
