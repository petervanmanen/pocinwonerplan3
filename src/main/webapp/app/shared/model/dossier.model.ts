import { IRaadsstuk } from 'app/shared/model/raadsstuk.model';

export interface IDossier {
  id?: number;
  naam?: string | null;
  hoortbijRaadsstuks?: IRaadsstuk[] | null;
}

export const defaultValue: Readonly<IDossier> = {};
