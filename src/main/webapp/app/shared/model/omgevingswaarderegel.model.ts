import { IOmgevingsnorm } from 'app/shared/model/omgevingsnorm.model';
import { IOmgevingswaarde } from 'app/shared/model/omgevingswaarde.model';

export interface IOmgevingswaarderegel {
  id?: number;
  groep?: string | null;
  naam?: string | null;
  beschrijftOmgevingsnorms?: IOmgevingsnorm[];
  beschrijftOmgevingswaardes?: IOmgevingswaarde[];
}

export const defaultValue: Readonly<IOmgevingswaarderegel> = {};
