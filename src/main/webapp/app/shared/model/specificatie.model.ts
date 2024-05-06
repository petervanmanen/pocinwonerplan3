import { IProjectactiviteit } from 'app/shared/model/projectactiviteit.model';
import { IVerzoek } from 'app/shared/model/verzoek.model';

export interface ISpecificatie {
  id?: number;
  antwoord?: string | null;
  groepering?: string | null;
  publiceerbaar?: string | null;
  vraagclassificatie?: string | null;
  vraagid?: string | null;
  vraagreferentie?: string | null;
  vraagtekst?: string | null;
  gedefinieerddoorProjectactiviteit?: IProjectactiviteit;
  bevatVerzoek?: IVerzoek | null;
}

export const defaultValue: Readonly<ISpecificatie> = {};
