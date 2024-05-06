import { INummeraanduiding } from 'app/shared/model/nummeraanduiding.model';

export interface IAdresaanduiding {
  id?: number;
  adresaanduiding?: string | null;
  verwijstnaarNummeraanduiding?: INummeraanduiding | null;
}

export const defaultValue: Readonly<IAdresaanduiding> = {};
