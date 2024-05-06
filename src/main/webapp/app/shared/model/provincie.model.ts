import dayjs from 'dayjs';

export interface IProvincie {
  id?: number;
  datumeindeprovincie?: dayjs.Dayjs | null;
  datumingangprovincie?: dayjs.Dayjs | null;
  hoofdstad?: string | null;
  oppervlakte?: string | null;
  oppervlakteland?: string | null;
  provinciecode?: string | null;
  provincienaam?: string | null;
}

export const defaultValue: Readonly<IProvincie> = {};
