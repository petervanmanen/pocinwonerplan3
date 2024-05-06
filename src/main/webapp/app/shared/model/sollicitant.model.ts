import { ISollicitatiegesprek } from 'app/shared/model/sollicitatiegesprek.model';

export interface ISollicitant {
  id?: number;
  kandidaatSollicitatiegespreks?: ISollicitatiegesprek[] | null;
}

export const defaultValue: Readonly<ISollicitant> = {};
