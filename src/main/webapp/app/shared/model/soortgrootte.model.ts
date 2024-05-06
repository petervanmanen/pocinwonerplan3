import dayjs from 'dayjs';

export interface ISoortgrootte {
  id?: number;
  codesoortgrootte?: string | null;
  datumbegingeldigheidsoortgrootte?: dayjs.Dayjs | null;
  datumeindegeldigheidsoortgrootte?: dayjs.Dayjs | null;
  naamsoortgrootte?: string | null;
}

export const defaultValue: Readonly<ISoortgrootte> = {};
