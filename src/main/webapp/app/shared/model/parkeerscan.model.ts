import { INaheffing } from 'app/shared/model/naheffing.model';
import { IMedewerker } from 'app/shared/model/medewerker.model';
import { IVoertuig } from 'app/shared/model/voertuig.model';
import { IParkeervlak } from 'app/shared/model/parkeervlak.model';

export interface IParkeerscan {
  id?: number;
  codegebruiker?: string | null;
  codescanvoertuig?: string | null;
  coordinaten?: string | null;
  foto?: string | null;
  kenteken?: string | null;
  parkeerrecht?: boolean | null;
  tijdstip?: string | null;
  transactieid?: string | null;
  komtvoortuitNaheffing?: INaheffing | null;
  uitgevoerddoorMedewerker?: IMedewerker | null;
  betreftVoertuig?: IVoertuig | null;
  betreftParkeervlak?: IParkeervlak | null;
}

export const defaultValue: Readonly<IParkeerscan> = {
  parkeerrecht: false,
};
