import { IDocument } from 'app/shared/model/document.model';
import { IZaak } from 'app/shared/model/zaak.model';
import { IBesluittype } from 'app/shared/model/besluittype.model';

export interface IBesluit {
  id?: number;
  besluit?: string | null;
  besluitidentificatie?: string | null;
  besluittoelichting?: string | null;
  datumbesluit?: string | null;
  datumpublicatie?: string | null;
  datumstart?: string | null;
  datumuiterlijkereactie?: string | null;
  datumverval?: string | null;
  datumverzending?: string | null;
  redenverval?: string | null;
  isvastgelegdinDocument?: IDocument | null;
  isuitkomstvanZaak?: IZaak | null;
  isvanBesluittype?: IBesluittype | null;
  kanvastgelegdzijnalsDocuments?: IDocument[] | null;
}

export const defaultValue: Readonly<IBesluit> = {};
