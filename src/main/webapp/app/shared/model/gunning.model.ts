import { IInschrijving } from 'app/shared/model/inschrijving.model';
import { IKandidaat } from 'app/shared/model/kandidaat.model';
import { IOfferte } from 'app/shared/model/offerte.model';
import { IMedewerker } from 'app/shared/model/medewerker.model';

export interface IGunning {
  id?: number;
  bericht?: string | null;
  datumgunning?: string | null;
  datumpublicatie?: string | null;
  datumvoorlopigegunning?: string | null;
  gegundeprijs?: string | null;
  betreftInschrijving?: IInschrijving | null;
  betreftKandidaat?: IKandidaat | null;
  betreftOfferte?: IOfferte | null;
  inhuurMedewerker?: IMedewerker | null;
}

export const defaultValue: Readonly<IGunning> = {};
