import { IBeperkingscoresoort } from 'app/shared/model/beperkingscoresoort.model';
import { IBeperking } from 'app/shared/model/beperking.model';

export interface IBeperkingscore {
  id?: number;
  commentaar?: string | null;
  score?: string | null;
  wet?: string | null;
  iseenBeperkingscoresoort?: IBeperkingscoresoort | null;
  emptyBeperking?: IBeperking | null;
}

export const defaultValue: Readonly<IBeperkingscore> = {};
