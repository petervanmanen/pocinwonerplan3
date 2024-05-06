import { IStraatsectie } from 'app/shared/model/straatsectie.model';

export interface IParkeervlak {
  id?: number;
  aantal?: string | null;
  coordinaten?: string | null;
  doelgroep?: string | null;
  fiscaal?: boolean | null;
  plaats?: string | null;
  vlakid?: string | null;
  bevatStraatsectie?: IStraatsectie | null;
}

export const defaultValue: Readonly<IParkeervlak> = {
  fiscaal: false,
};
