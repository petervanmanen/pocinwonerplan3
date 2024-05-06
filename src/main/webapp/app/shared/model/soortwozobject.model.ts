import dayjs from 'dayjs';

export interface ISoortwozobject {
  id?: number;
  datumbegingeldigheidsoortobjectcode?: dayjs.Dayjs | null;
  datumeindegeldigheidsoortobjectcode?: dayjs.Dayjs | null;
  naamsoortobjectcode?: string | null;
  opmerkingensoortobjectcode?: string | null;
  soortobjectcode?: string | null;
}

export const defaultValue: Readonly<ISoortwozobject> = {};
