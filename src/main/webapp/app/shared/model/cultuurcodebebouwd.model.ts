import dayjs from 'dayjs';

export interface ICultuurcodebebouwd {
  id?: number;
  cultuurcodebebouwd?: string | null;
  datumbegingeldigheidcultuurcodebebouwd?: dayjs.Dayjs | null;
  datumeindegeldigheidcultuurcodebebouwd?: dayjs.Dayjs | null;
  naamcultuurcodebebouwd?: string | null;
}

export const defaultValue: Readonly<ICultuurcodebebouwd> = {};
