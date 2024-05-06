import dayjs from 'dayjs';
import { IUitstroomreden } from 'app/shared/model/uitstroomreden.model';
import { IResultaat } from 'app/shared/model/resultaat.model';
import { IProjectsoort } from 'app/shared/model/projectsoort.model';
import { ILocatie } from 'app/shared/model/locatie.model';
import { IKostenplaats } from 'app/shared/model/kostenplaats.model';
import { ITraject } from 'app/shared/model/traject.model';

export interface IProject {
  id?: number;
  coordinaten?: string | null;
  datumeinde?: dayjs.Dayjs | null;
  datumstart?: dayjs.Dayjs | null;
  jaartot?: string | null;
  jaarvan?: string | null;
  locatie?: string | null;
  naam?: string | null;
  naamcode?: string | null;
  projectcd?: string | null;
  toponiem?: string | null;
  trefwoorden?: string | null;
  heeftuitstroomredenUitstroomreden?: IUitstroomreden;
  heeftresultaatResultaat?: IResultaat;
  soortprojectProjectsoort?: IProjectsoort | null;
  wordtbegrensddoorLocaties?: ILocatie[] | null;
  heeftKostenplaats?: IKostenplaats[] | null;
  heeftprojectTraject?: ITraject | null;
}

export const defaultValue: Readonly<IProject> = {};
