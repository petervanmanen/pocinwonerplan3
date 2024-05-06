import { IBinnenlocatie } from 'app/shared/model/binnenlocatie.model';

export interface ISportmateriaal {
  id?: number;
  naam?: string | null;
  heeftBinnenlocaties?: IBinnenlocatie[] | null;
}

export const defaultValue: Readonly<ISportmateriaal> = {};
