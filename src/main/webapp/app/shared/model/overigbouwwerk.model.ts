import dayjs from 'dayjs';

export interface IOverigbouwwerk {
  id?: number;
  datumbegingeldigheidoverigbouwwerk?: dayjs.Dayjs | null;
  datumeindegeldigheidoverigbouwwerk?: dayjs.Dayjs | null;
  geometrieoverigbouwwerk?: string | null;
  identificatieoverigbouwwerk?: string | null;
  lod0geometrieoverigbouwwerk?: string | null;
  lod1geometrieoverigbouwwerk?: string | null;
  lod2geometrieoverigbouwwerk?: string | null;
  lod3geometrieoverigbouwwerk?: string | null;
  relatievehoogteliggingoverigbouwwerk?: string | null;
  statusoverigbouwwerk?: string | null;
}

export const defaultValue: Readonly<IOverigbouwwerk> = {};
