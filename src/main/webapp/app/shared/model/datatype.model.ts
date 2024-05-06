import dayjs from 'dayjs';

export interface IDatatype {
  datumopname?: dayjs.Dayjs | null;
  definitie?: string | null;
  domein?: string | null;
  eaguid?: string | null;
  herkomst?: string | null;
  id?: string;
  kardinaliteit?: string | null;
  lengte?: string | null;
  naam?: string | null;
  patroon?: string | null;
  toelichting?: string | null;
}

export const defaultValue: Readonly<IDatatype> = {};
