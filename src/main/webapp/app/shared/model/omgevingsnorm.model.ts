import { IOmgevingswaarderegel } from 'app/shared/model/omgevingswaarderegel.model';

export interface IOmgevingsnorm {
  id?: number;
  naam?: string | null;
  omgevingsnormgroep?: string | null;
  beschrijftOmgevingswaarderegels?: IOmgevingswaarderegel[] | null;
}

export const defaultValue: Readonly<IOmgevingsnorm> = {};
