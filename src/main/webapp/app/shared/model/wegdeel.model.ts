import dayjs from 'dayjs';
import { IStremming } from 'app/shared/model/stremming.model';

export interface IWegdeel {
  id?: number;
  datumbegingeldigheidwegdeel?: dayjs.Dayjs | null;
  datumeindegeldigheidwegdeel?: dayjs.Dayjs | null;
  functiewegdeel?: string | null;
  fysiekvoorkomenwegdeel?: string | null;
  geometriewegdeel?: string | null;
  identificatiewegdeel?: string | null;
  kruinlijngeometriewegdeel?: string | null;
  lod0geometriewegdeel?: string | null;
  plusfunctiewegdeel?: string | null;
  plusfysiekvoorkomenwegdeel?: string | null;
  relatievehoogteliggingwegdeel?: string | null;
  statuswegdeel?: string | null;
  wegdeeloptalud?: string | null;
  betreftStremmings?: IStremming[] | null;
}

export const defaultValue: Readonly<IWegdeel> = {};
