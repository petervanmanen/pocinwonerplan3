import dayjs from 'dayjs';

export interface IBegroeidterreindeel {
  id?: number;
  begroeidterreindeeloptalud?: string | null;
  datumbegingeldigheidbegroeidterreindeel?: dayjs.Dayjs | null;
  datumeindegeldigheidbegroeidterreindeel?: dayjs.Dayjs | null;
  fysiekvoorkomenbegroeidterreindeel?: string | null;
  geometriebegroeidterreindeel?: string | null;
  identificatiebegroeidterreindeel?: string | null;
  kruinlijngeometriebegroeidterreindeel?: string | null;
  lod0geometriebegroeidterreindeel?: string | null;
  plusfysiekvoorkomenbegroeidterreindeel?: string | null;
  relatievehoogteliggingbegroeidterreindeel?: string | null;
  statusbegroeidterreindeel?: string | null;
}

export const defaultValue: Readonly<IBegroeidterreindeel> = {};
