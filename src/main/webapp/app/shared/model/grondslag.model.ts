import { IZaak } from 'app/shared/model/zaak.model';

export interface IGrondslag {
  id?: number;
  code?: string | null;
  omschrijving?: string | null;
  heeftZaaks?: IZaak[];
}

export const defaultValue: Readonly<IGrondslag> = {};
