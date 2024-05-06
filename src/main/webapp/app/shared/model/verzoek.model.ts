import dayjs from 'dayjs';
import { IZaak } from 'app/shared/model/zaak.model';
import { IProjectactiviteit } from 'app/shared/model/projectactiviteit.model';
import { IProjectlocatie } from 'app/shared/model/projectlocatie.model';
import { IActiviteit } from 'app/shared/model/activiteit.model';
import { ILocatie } from 'app/shared/model/locatie.model';
import { IInitiatiefnemer } from 'app/shared/model/initiatiefnemer.model';

export interface IVerzoek {
  id?: number;
  akkoordverklaring?: boolean | null;
  ambtshalve?: boolean | null;
  datumindiening?: dayjs.Dayjs | null;
  doel?: string | null;
  naam?: string | null;
  referentieaanvrager?: string | null;
  toelichtinglateraantelevereninformatie?: string | null;
  toelichtingnietaantelevereninformatie?: string | null;
  toelichtingverzoek?: string | null;
  type?: string | null;
  verzoeknummer?: string | null;
  volgnummer?: string | null;
  leidttotZaak?: IZaak | null;
  betrefteerderverzoekVerzoek?: IVerzoek | null;
  betreftProjectactiviteits?: IProjectactiviteit[];
  betreftProjectlocaties?: IProjectlocatie[];
  betreftActiviteits?: IActiviteit[] | null;
  betreftLocaties?: ILocatie[] | null;
  heeftalsverantwoordelijkeInitiatiefnemer?: IInitiatiefnemer;
}

export const defaultValue: Readonly<IVerzoek> = {
  akkoordverklaring: false,
  ambtshalve: false,
};
