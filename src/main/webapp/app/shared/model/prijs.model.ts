import dayjs from 'dayjs';
import { IProduct } from 'app/shared/model/product.model';

export interface IPrijs {
  id?: number;
  bedrag?: number | null;
  datumeindegeldigheid?: dayjs.Dayjs | null;
  datumstart?: dayjs.Dayjs | null;
  heeftprijsProduct?: IProduct;
}

export const defaultValue: Readonly<IPrijs> = {};
