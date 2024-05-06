import dayjs from 'dayjs';
import { IMuseumobject } from 'app/shared/model/museumobject.model';

export interface IBelanghebbende {
  id?: number;
  datumstart?: dayjs.Dayjs | null;
  datumtot?: dayjs.Dayjs | null;
  heeftMuseumobjects?: IMuseumobject[] | null;
}

export const defaultValue: Readonly<IBelanghebbende> = {};
