import dayjs from 'dayjs';

export interface ICultuurcodeonbebouwd {
  id?: number;
  cultuurcodeonbebouwd?: string | null;
  datumbegingeldigheidcultuurcodeonbebouwd?: dayjs.Dayjs | null;
  datumeindegeldigheidcultuurcodeonbebouwd?: dayjs.Dayjs | null;
  naamcultuurcodeonbebouwd?: string | null;
}

export const defaultValue: Readonly<ICultuurcodeonbebouwd> = {};
