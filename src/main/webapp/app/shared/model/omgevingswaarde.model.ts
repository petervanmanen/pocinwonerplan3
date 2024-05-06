import { IOmgevingswaarderegel } from 'app/shared/model/omgevingswaarderegel.model';

export interface IOmgevingswaarde {
  id?: number;
  naam?: string | null;
  omgevingswaardegroep?: string | null;
  beschrijftOmgevingswaarderegels?: IOmgevingswaarderegel[] | null;
}

export const defaultValue: Readonly<IOmgevingswaarde> = {};
