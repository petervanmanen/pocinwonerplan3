import dayjs from 'dayjs';
import { ILeverancier } from 'app/shared/model/leverancier.model';
import { IApplicatie } from 'app/shared/model/applicatie.model';
import { IZaak } from 'app/shared/model/zaak.model';

export interface IMedewerker {
  id?: number;
  achternaam?: string | null;
  datumindienst?: dayjs.Dayjs | null;
  datumuitdienst?: string | null;
  emailadres?: string | null;
  extern?: string | null;
  functie?: string | null;
  geslachtsaanduiding?: string | null;
  medewerkeridentificatie?: string | null;
  medewerkertoelichting?: string | null;
  roepnaam?: string | null;
  telefoonnummer?: string | null;
  voorletters?: string | null;
  voorvoegselachternaam?: string | null;
  geleverdviaLeverancier?: ILeverancier | null;
  rollenApplicaties?: IApplicatie[] | null;
  afhandelendmedewerkerZaaks?: IZaak[] | null;
}

export const defaultValue: Readonly<IMedewerker> = {};
