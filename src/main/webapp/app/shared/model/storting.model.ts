import { IMilieustraat } from 'app/shared/model/milieustraat.model';
import { IFractie } from 'app/shared/model/fractie.model';
import { IPas } from 'app/shared/model/pas.model';

export interface IStorting {
  id?: number;
  datumtijd?: string | null;
  gewicht?: string | null;
  bijMilieustraat?: IMilieustraat | null;
  fractieFracties?: IFractie[] | null;
  uitgevoerdestortingPas?: IPas | null;
}

export const defaultValue: Readonly<IStorting> = {};
