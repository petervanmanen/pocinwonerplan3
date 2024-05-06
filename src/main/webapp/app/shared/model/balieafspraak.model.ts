import { IKlantcontact } from 'app/shared/model/klantcontact.model';
import { IAfspraakstatus } from 'app/shared/model/afspraakstatus.model';
import { IMedewerker } from 'app/shared/model/medewerker.model';
import { IZaak } from 'app/shared/model/zaak.model';

export interface IBalieafspraak {
  id?: number;
  eindtijdgepland?: string | null;
  notitie?: string | null;
  starttijdgepland?: string | null;
  tijdaangemaakt?: string | null;
  tijdsduurgepland?: string | null;
  toelichting?: string | null;
  wachttijdnastartafspraak?: string | null;
  wachttijdtotaal?: string | null;
  wachttijdvoorstartafspraak?: string | null;
  werkelijketijdsduur?: string | null;
  mondtuitinKlantcontact?: IKlantcontact | null;
  heeftAfspraakstatus?: IAfspraakstatus | null;
  metMedewerker?: IMedewerker | null;
  heeftbetrekkingopZaak?: IZaak | null;
}

export const defaultValue: Readonly<IBalieafspraak> = {};
