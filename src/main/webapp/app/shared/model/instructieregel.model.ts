import { IGebiedsaanwijzing } from 'app/shared/model/gebiedsaanwijzing.model';

export interface IInstructieregel {
  id?: number;
  instructieregelinstrument?: string | null;
  instructieregeltaakuitoefening?: string | null;
  beschrijftgebiedsaanwijzingGebiedsaanwijzings?: IGebiedsaanwijzing[] | null;
}

export const defaultValue: Readonly<IInstructieregel> = {};
