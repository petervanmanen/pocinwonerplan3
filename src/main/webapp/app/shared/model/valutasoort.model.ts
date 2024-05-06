import dayjs from 'dayjs';

export interface IValutasoort {
  id?: number;
  datumbegingeldigheidvalutasoort?: dayjs.Dayjs | null;
  datumeindegeldigheidvalutasoort?: dayjs.Dayjs | null;
  naamvaluta?: string | null;
  valutacode?: string | null;
}

export const defaultValue: Readonly<IValutasoort> = {};
