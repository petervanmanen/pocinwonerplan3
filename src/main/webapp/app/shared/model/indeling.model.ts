import { IArchief } from 'app/shared/model/archief.model';

export interface IIndeling {
  id?: number;
  indelingsoort?: string | null;
  naam?: string | null;
  nummer?: string | null;
  omschrijving?: string | null;
  hoortbijArchief?: IArchief | null;
  valtbinnenIndeling2?: IIndeling | null;
}

export const defaultValue: Readonly<IIndeling> = {};
