import { IBuurt } from 'app/shared/model/buurt.model';
import { INummeraanduiding } from 'app/shared/model/nummeraanduiding.model';

export interface IGebied {
  id?: number;
  gebied?: string | null;
  komtovereenBuurt?: IBuurt | null;
  ligtinNummeraanduidings?: INummeraanduiding[] | null;
}

export const defaultValue: Readonly<IGebied> = {};
