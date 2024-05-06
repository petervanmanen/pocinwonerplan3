import { ICategorie } from 'app/shared/model/categorie.model';
import { IContainertype } from 'app/shared/model/containertype.model';
import { IFractie } from 'app/shared/model/fractie.model';
import { ILocatie } from 'app/shared/model/locatie.model';
import { IMedewerker } from 'app/shared/model/medewerker.model';
import { ILeverancier } from 'app/shared/model/leverancier.model';
import { IBeheerobject } from 'app/shared/model/beheerobject.model';
import { ISchouwronde } from 'app/shared/model/schouwronde.model';

export interface IMelding {
  id?: number;
  vierentwintiguurs?: string | null;
  datumtijd?: string | null;
  illegaal?: string | null;
  meldingnummer?: string | null;
  omschrijving?: string | null;
  hoofdcategorieCategorie?: ICategorie | null;
  subcategorieCategorie?: ICategorie | null;
  betreftContainertype?: IContainertype | null;
  betreftFractie?: IFractie | null;
  betreftLocatie?: ILocatie | null;
  melderMedewerker?: IMedewerker | null;
  uitvoerderLeverancier?: ILeverancier | null;
  uitvoerderMedewerker?: IMedewerker | null;
  betreftBeheerobjects?: IBeheerobject[] | null;
  heeftSchouwronde?: ISchouwronde | null;
}

export const defaultValue: Readonly<IMelding> = {};
