import { IZaaktype } from 'app/shared/model/zaaktype.model';
import { IOnderwerp } from 'app/shared/model/onderwerp.model';
import { IProduct } from 'app/shared/model/product.model';

export interface IDienst {
  id?: number;
  startZaaktype?: IZaaktype | null;
  heeftOnderwerp?: IOnderwerp | null;
  betreftProduct?: IProduct | null;
}

export const defaultValue: Readonly<IDienst> = {};
