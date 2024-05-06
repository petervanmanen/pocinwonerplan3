import { IFractie } from 'app/shared/model/fractie.model';
import { IPas } from 'app/shared/model/pas.model';

export interface IMilieustraat {
  id?: number;
  adresaanduiding?: string | null;
  naam?: string | null;
  omschrijving?: string | null;
  inzamelpuntvanFracties?: IFractie[] | null;
  geldigvoorPas?: IPas[];
}

export const defaultValue: Readonly<IMilieustraat> = {};
