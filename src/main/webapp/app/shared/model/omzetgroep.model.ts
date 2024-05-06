import { IProduct } from 'app/shared/model/product.model';

export interface IOmzetgroep {
  id?: number;
  naam?: string | null;
  omschrijving?: string | null;
  valtbinnenProducts?: IProduct[] | null;
}

export const defaultValue: Readonly<IOmzetgroep> = {};
