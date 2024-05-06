import dayjs from 'dayjs';

export interface IOverbruggingsdeel {
  id?: number;
  datumbegingeldigheidoverbruggingsdeel?: dayjs.Dayjs | null;
  datumeindegeldigheidoverbruggingsdeel?: dayjs.Dayjs | null;
  geometrieoverbruggingsdeel?: string | null;
  hoortbijtypeoverbrugging?: string | null;
  identificatieoverbruggingsdeel?: string | null;
  lod0geometrieoverbruggingsdeel?: string | null;
  overbruggingisbeweegbaar?: string | null;
  relatievehoogteliggingoverbruggingsdeel?: string | null;
  statusoverbruggingsdeel?: string | null;
  typeoverbruggingsdeel?: string | null;
}

export const defaultValue: Readonly<IOverbruggingsdeel> = {};
