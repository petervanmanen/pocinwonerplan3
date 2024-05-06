import { IAanbesteding } from 'app/shared/model/aanbesteding.model';

export interface IAankondiging {
  id?: number;
  beschrijving?: string | null;
  categorie?: string | null;
  datum?: string | null;
  naam?: string | null;
  type?: string | null;
  mondtuitAanbesteding?: IAanbesteding | null;
}

export const defaultValue: Readonly<IAankondiging> = {};
