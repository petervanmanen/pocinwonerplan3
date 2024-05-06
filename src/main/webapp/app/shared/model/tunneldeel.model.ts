import dayjs from 'dayjs';

export interface ITunneldeel {
  id?: number;
  datumbegingeldigheidtunneldeel?: dayjs.Dayjs | null;
  datumeindegeldigheidtunneldeel?: dayjs.Dayjs | null;
  geometrietunneldeel?: string | null;
  identificatietunneldeel?: string | null;
  lod0geometrietunneldeel?: string | null;
  relatievehoogteliggingtunneldeel?: string | null;
  statustunneldeel?: string | null;
}

export const defaultValue: Readonly<ITunneldeel> = {};
