import dayjs from 'dayjs';

export interface IWaterdeel {
  id?: number;
  datumbegingeldigheidwaterdeel?: dayjs.Dayjs | null;
  datumeindegeldigheidwaterdeel?: dayjs.Dayjs | null;
  geometriewaterdeel?: string | null;
  identificatiewaterdeel?: string | null;
  plustypewaterdeel?: string | null;
  relatievehoogteliggingwaterdeel?: string | null;
  statuswaterdeel?: string | null;
  typewaterdeel?: string | null;
}

export const defaultValue: Readonly<IWaterdeel> = {};
