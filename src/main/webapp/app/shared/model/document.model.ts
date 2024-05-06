import { IIdentificatiekenmerk } from 'app/shared/model/identificatiekenmerk.model';
import { IDocumenttype } from 'app/shared/model/documenttype.model';
import { IBinnenlocatie } from 'app/shared/model/binnenlocatie.model';
import { IRapportagemoment } from 'app/shared/model/rapportagemoment.model';
import { IApplicatie } from 'app/shared/model/applicatie.model';
import { IBesluit } from 'app/shared/model/besluit.model';
import { IZaak } from 'app/shared/model/zaak.model';

export interface IDocument {
  id?: number;
  cocumentbeschrijving?: string | null;
  datumcreatiedocument?: string | null;
  datumontvangstdocument?: string | null;
  datumverzendingdocument?: string | null;
  documentauteur?: string | null;
  documentidentificatie?: string | null;
  documenttitel?: string | null;
  vertrouwelijkaanduiding?: string | null;
  heeftkenmerkIdentificatiekenmerk?: IIdentificatiekenmerk | null;
  isvanDocumenttype?: IDocumenttype | null;
  inspectierapportBinnenlocatie?: IBinnenlocatie | null;
  heeftRapportagemoment?: IRapportagemoment | null;
  heeftdocumentenApplicaties?: IApplicatie[] | null;
  kanvastgelegdzijnalsBesluits?: IBesluit[] | null;
  kentZaaks?: IZaak[];
}

export const defaultValue: Readonly<IDocument> = {};
