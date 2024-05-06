import dayjs from 'dayjs';

export interface IOndersteunendwegdeel {
  id?: number;
  datumbegingeldigheidondersteunendwegdeel?: dayjs.Dayjs | null;
  datumeindegeldigheidondersteunendwegdeel?: dayjs.Dayjs | null;
  functieondersteunendwegdeel?: string | null;
  fysiekvoorkomenondersteunendwegdeel?: string | null;
  geometrieondersteunendwegdeel?: string | null;
  identificatieondersteunendwegdeel?: string | null;
  kruinlijngeometrieondersteunendwegdeel?: string | null;
  lod0geometrieondersteunendwegdeel?: string | null;
  ondersteunendwegdeeloptalud?: string | null;
  plusfunctieondersteunendwegdeel?: string | null;
  plusfysiekvoorkomenondersteunendwegdeel?: string | null;
  relatievehoogteliggingondersteunendwegdeel?: string | null;
  statusondersteunendwegdeel?: string | null;
}

export const defaultValue: Readonly<IOndersteunendwegdeel> = {};
