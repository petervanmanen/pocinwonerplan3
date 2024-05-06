import { IBeperkingscategorie } from 'app/shared/model/beperkingscategorie.model';
import { IBeschikking } from 'app/shared/model/beschikking.model';

export interface IBeperking {
  id?: number;
  categorie?: string | null;
  commentaar?: string | null;
  duur?: string | null;
  wet?: string | null;
  iseenBeperkingscategorie?: IBeperkingscategorie | null;
  isgebaseerdopBeschikking?: IBeschikking | null;
}

export const defaultValue: Readonly<IBeperking> = {};
