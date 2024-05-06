import dayjs from 'dayjs';
import { IHotel } from 'app/shared/model/hotel.model';

export interface IHotelbezoek {
  id?: number;
  datumeinde?: dayjs.Dayjs | null;
  datumstart?: dayjs.Dayjs | null;
  heeftHotel?: IHotel | null;
}

export const defaultValue: Readonly<IHotelbezoek> = {};
