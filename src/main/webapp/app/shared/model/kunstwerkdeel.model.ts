import dayjs from 'dayjs';

export interface IKunstwerkdeel {
  id?: number;
  datumbegingeldigheidkunstwerkdeel?: dayjs.Dayjs | null;
  datumeindegeldigheidkunstwerkdeel?: dayjs.Dayjs | null;
  geometriekunstwerkdeel?: string | null;
  identificatiekunstwerkdeel?: string | null;
  lod0geometriekunstwerkdeel?: string | null;
  lod1geometriekunstwerkdeel?: string | null;
  lod2geometriekunstwerkdeel?: string | null;
  lod3geometriekunstwerkdeel?: string | null;
  relatievehoogteliggingkunstwerkdeel?: string | null;
  statuskunstwerkdeel?: string | null;
}

export const defaultValue: Readonly<IKunstwerkdeel> = {};
