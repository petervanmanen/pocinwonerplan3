import { IProduct } from 'app/shared/model/product.model';

export interface IProductgroep {
  id?: number;
  beslisboom?: string | null;
  code?: string | null;
  omschrijving?: string | null;
  valtbinnenProducts?: IProduct[] | null;
}

export const defaultValue: Readonly<IProductgroep> = {};
