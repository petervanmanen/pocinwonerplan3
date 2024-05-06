import { ILeverancier } from 'app/shared/model/leverancier.model';
import { IDeclaratiesoort } from 'app/shared/model/declaratiesoort.model';
import { IWerknemer } from 'app/shared/model/werknemer.model';

export interface IDeclaratie {
  id?: number;
  datumdeclaratie?: string | null;
  declaratiebedrag?: string | null;
  declaratiestatus?: string | null;
  ingedienddoorLeverancier?: ILeverancier | null;
  soortdeclaratieDeclaratiesoort?: IDeclaratiesoort | null;
  dientinWerknemer?: IWerknemer | null;
}

export const defaultValue: Readonly<IDeclaratie> = {};
