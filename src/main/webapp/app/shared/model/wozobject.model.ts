import dayjs from 'dayjs';

export interface IWozobject {
  id?: number;
  empty?: string | null;
  datumbegingeldigheidwozobject?: dayjs.Dayjs | null;
  datumeindegeldigheidwozobject?: dayjs.Dayjs | null;
  datumwaardepeiling?: dayjs.Dayjs | null;
  gebruikscode?: string | null;
  geometriewozobject?: string | null;
  grondoppervlakte?: string | null;
  soortobjectcode?: string | null;
  statuswozobject?: string | null;
  vastgesteldewaarde?: string | null;
  wozobjectnummer?: string | null;
}

export const defaultValue: Readonly<IWozobject> = {};
