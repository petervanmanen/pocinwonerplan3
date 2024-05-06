import dayjs from 'dayjs';

export interface IAanduidingverblijfsrecht {
  id?: number;
  datumaanvanggeldigheidverblijfsrecht?: dayjs.Dayjs | null;
  datumeindegeldigheidverblijfsrecht?: dayjs.Dayjs | null;
  verblijfsrechtnummer?: string | null;
  verblijfsrechtomschrijving?: string | null;
}

export const defaultValue: Readonly<IAanduidingverblijfsrecht> = {};
