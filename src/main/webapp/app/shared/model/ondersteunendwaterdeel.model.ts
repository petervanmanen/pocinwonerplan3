import dayjs from 'dayjs';

export interface IOndersteunendwaterdeel {
  id?: number;
  datumbegingeldigheidondersteunendwaterdeel?: dayjs.Dayjs | null;
  datumeindegeldigheidondersteunendwaterdeel?: dayjs.Dayjs | null;
  geometrieondersteunendwaterdeel?: string | null;
  identificatieondersteunendwaterdeel?: string | null;
  plustypeondersteunendwaterdeel?: string | null;
  relatievehoogteliggingondersteunendwaterdeel?: string | null;
  statusondersteunendwaterdeel?: string | null;
  typeondersteunendwaterdeel?: string | null;
}

export const defaultValue: Readonly<IOndersteunendwaterdeel> = {};
