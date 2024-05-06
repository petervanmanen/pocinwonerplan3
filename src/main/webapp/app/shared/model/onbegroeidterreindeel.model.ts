import dayjs from 'dayjs';

export interface IOnbegroeidterreindeel {
  id?: number;
  datumbegingeldigheidonbegroeidterreindeel?: dayjs.Dayjs | null;
  datumeindegeldigheidonbegroeidterreindeel?: dayjs.Dayjs | null;
  fysiekvoorkomenonbegroeidterreindeel?: string | null;
  geometrieonbegroeidterreindeel?: string | null;
  identificatieonbegroeidterreindeel?: string | null;
  kruinlijngeometrieonbegroeidterreindeel?: string | null;
  onbegroeidterreindeeloptalud?: string | null;
  plusfysiekvoorkomenonbegroeidterreindeel?: string | null;
  relatievehoogteliggingonbegroeidterreindeel?: string | null;
  statusonbegroeidterreindeel?: string | null;
}

export const defaultValue: Readonly<IOnbegroeidterreindeel> = {};
