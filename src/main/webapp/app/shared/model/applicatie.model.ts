import { ILeverancier } from 'app/shared/model/leverancier.model';
import { IDocument } from 'app/shared/model/document.model';
import { IMedewerker } from 'app/shared/model/medewerker.model';

export interface IApplicatie {
  id?: number;
  applicatieurl?: string | null;
  beheerstatus?: string | null;
  beleidsdomein?: string | null;
  categorie?: string | null;
  guid?: string | null;
  naam?: string | null;
  omschrijving?: string | null;
  packagingstatus?: string | null;
  heeftleverancierLeverancier?: ILeverancier | null;
  heeftdocumentenDocuments?: IDocument[] | null;
  rollenMedewerkers?: IMedewerker[] | null;
}

export const defaultValue: Readonly<IApplicatie> = {};
