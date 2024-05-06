import { ICategorie } from 'app/shared/model/categorie.model';

export interface ILeverancier {
  id?: number;
  agbcode?: string | null;
  leverancierscode?: string | null;
  naam?: string | null;
  soortleverancier?: string | null;
  soortleveranciercode?: string | null;
  gekwalificeerdCategories?: ICategorie[] | null;
}

export const defaultValue: Readonly<ILeverancier> = {};
