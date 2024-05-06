import dayjs from 'dayjs';

export interface ILocatieaanduidingwozobject {
  id?: number;
  datumbegingeldigheid?: dayjs.Dayjs | null;
  datumeindegeldigheid?: dayjs.Dayjs | null;
  locatieomschrijving?: string | null;
  primair?: string | null;
}

export const defaultValue: Readonly<ILocatieaanduidingwozobject> = {};
