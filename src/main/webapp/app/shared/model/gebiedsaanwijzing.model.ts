import { ILocatie } from 'app/shared/model/locatie.model';
import { IInstructieregel } from 'app/shared/model/instructieregel.model';

export interface IGebiedsaanwijzing {
  id?: number;
  groep?: string | null;
  naam?: string | null;
  nen3610id?: string | null;
  verwijstnaarLocaties?: ILocatie[] | null;
  beschrijftgebiedsaanwijzingInstructieregels?: IInstructieregel[] | null;
}

export const defaultValue: Readonly<IGebiedsaanwijzing> = {};
