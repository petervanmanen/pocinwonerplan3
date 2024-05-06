import { IZaaktype } from 'app/shared/model/zaaktype.model';

export interface IFormuliersoort {
  id?: number;
  ingebruik?: string | null;
  naam?: string | null;
  onderwerp?: string | null;
  isaanleidingvoorZaaktypes?: IZaaktype[] | null;
}

export const defaultValue: Readonly<IFormuliersoort> = {};
