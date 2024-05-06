import { ILocatie } from 'app/shared/model/locatie.model';
import { INorm } from 'app/shared/model/norm.model';

export interface INormwaarde {
  id?: number;
  kwalitatievewaarde?: string | null;
  kwantitatievewaardeeenheid?: string | null;
  kwantitatievewaardeomvang?: string | null;
  geldtvoorLocaties?: ILocatie[] | null;
  bevatNorm?: INorm;
}

export const defaultValue: Readonly<INormwaarde> = {};
