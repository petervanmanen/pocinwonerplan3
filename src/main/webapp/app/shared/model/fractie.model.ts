import { IMilieustraat } from 'app/shared/model/milieustraat.model';
import { IStorting } from 'app/shared/model/storting.model';

export interface IFractie {
  id?: number;
  naam?: string | null;
  omschrijving?: string | null;
  inzamelpuntvanMilieustraats?: IMilieustraat[] | null;
  fractieStortings?: IStorting[];
}

export const defaultValue: Readonly<IFractie> = {};
