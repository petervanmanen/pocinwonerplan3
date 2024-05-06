import { IBuurt } from 'app/shared/model/buurt.model';
import { IWijk } from 'app/shared/model/wijk.model';
import { ISchouwronde } from 'app/shared/model/schouwronde.model';

export interface IAreaal {
  id?: number;
  geometrie?: string | null;
  ligtinBuurts?: IBuurt[] | null;
  valtbinnenWijks?: IWijk[] | null;
  binnenSchouwrondes?: ISchouwronde[];
}

export const defaultValue: Readonly<IAreaal> = {};
