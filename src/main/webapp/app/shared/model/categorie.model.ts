import { ILeverancier } from 'app/shared/model/leverancier.model';

export interface ICategorie {
  id?: number;
  naam?: string | null;
  gekwalificeerdLeveranciers?: ILeverancier[] | null;
}

export const defaultValue: Readonly<ICategorie> = {};
