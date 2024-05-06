import dayjs from 'dayjs';
import { IProduct } from 'app/shared/model/product.model';

export interface IWinkelvoorraaditem {
  id?: number;
  aantal?: string | null;
  aantalinbestelling?: string | null;
  datumleveringbestelling?: dayjs.Dayjs | null;
  locatie?: string | null;
  betreftProduct?: IProduct | null;
}

export const defaultValue: Readonly<IWinkelvoorraaditem> = {};
