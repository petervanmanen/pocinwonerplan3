import { IPut } from 'app/shared/model/put.model';

export interface IVlak {
  id?: number;
  dieptetot?: string | null;
  dieptevan?: string | null;
  key?: string | null;
  keyput?: string | null;
  projectcd?: string | null;
  putnummer?: string | null;
  vlaknummer?: string | null;
  heeftPut?: IPut | null;
}

export const defaultValue: Readonly<IVlak> = {};
