import { IResultaat } from 'app/shared/model/resultaat.model';
import { IUitstroomreden } from 'app/shared/model/uitstroomreden.model';
import { ITrajectsoort } from 'app/shared/model/trajectsoort.model';
import { IClient } from 'app/shared/model/client.model';

export interface ITraject {
  id?: number;
  datumeinde?: string | null;
  datumstart?: string | null;
  datumtoekenning?: string | null;
  naam?: string | null;
  omschrijving?: string | null;
  resultaat?: string | null;
  heeftresultaatResultaat?: IResultaat;
  heeftuitstroomredenUitstroomreden?: IUitstroomreden;
  istrajectsoortTrajectsoort?: ITrajectsoort | null;
  heeftparticipatietrajectClient?: IClient | null;
  heefttrajectClient?: IClient | null;
}

export const defaultValue: Readonly<ITraject> = {};
