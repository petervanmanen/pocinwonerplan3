import { IBijzonderheidsoort } from 'app/shared/model/bijzonderheidsoort.model';

export interface IBijzonderheid {
  id?: number;
  omschrijving?: string | null;
  isvansoortBijzonderheidsoort?: IBijzonderheidsoort | null;
}

export const defaultValue: Readonly<IBijzonderheid> = {};
