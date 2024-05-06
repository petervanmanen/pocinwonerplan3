import { IBetrokkene } from 'app/shared/model/betrokkene.model';
import { IZaak } from 'app/shared/model/zaak.model';
import { IMedewerker } from 'app/shared/model/medewerker.model';
import { ITelefoononderwerp } from 'app/shared/model/telefoononderwerp.model';
import { ITelefoontje } from 'app/shared/model/telefoontje.model';

export interface IKlantcontact {
  id?: number;
  eindtijd?: string | null;
  kanaal?: string | null;
  notitie?: string | null;
  starttijd?: string | null;
  tijdsduur?: string | null;
  toelichting?: string | null;
  wachttijdtotaal?: string | null;
  heeftklantcontactenBetrokkene?: IBetrokkene | null;
  heeftbetrekkingopZaak?: IZaak | null;
  isgevoerddoorMedewerker?: IMedewerker | null;
  heeftTelefoononderwerp?: ITelefoononderwerp | null;
  mondtuitinTelefoontje?: ITelefoontje | null;
}

export const defaultValue: Readonly<IKlantcontact> = {};
