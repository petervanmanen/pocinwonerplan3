import dayjs from 'dayjs';

export interface IOverigescheiding {
  id?: number;
  datumbegingeldigheidoverigescheiding?: dayjs.Dayjs | null;
  datumeindegeldigheidoverigescheiding?: dayjs.Dayjs | null;
  geometrieoverigescheiding?: string | null;
  identificatieoverigescheiding?: string | null;
  lod0geometrieoverigescheiding?: string | null;
  lod1geometrieoverigescheiding?: string | null;
  lod2geometrieoverigescheiding?: string | null;
  lod3geometrieoverigescheiding?: string | null;
  relatievehoogteliggingoverigescheiding?: string | null;
  statusoverigescheiding?: string | null;
  typeoverigescheiding?: string | null;
}

export const defaultValue: Readonly<IOverigescheiding> = {};
