import { IBinnenlocatie } from 'app/shared/model/binnenlocatie.model';
import { IVeld } from 'app/shared/model/veld.model';

export interface IBelijning {
  id?: number;
  naam?: string | null;
  heeftBinnenlocaties?: IBinnenlocatie[] | null;
  heeftVelds?: IVeld[] | null;
}

export const defaultValue: Readonly<IBelijning> = {};
