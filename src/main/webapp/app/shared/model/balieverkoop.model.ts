import { IPrijs } from 'app/shared/model/prijs.model';
import { IProduct } from 'app/shared/model/product.model';

export interface IBalieverkoop {
  id?: number;
  aantal?: string | null;
  kanaal?: string | null;
  verkooptijd?: string | null;
  tegenprijsPrijs?: IPrijs | null;
  betreftProduct?: IProduct | null;
}

export const defaultValue: Readonly<IBalieverkoop> = {};
