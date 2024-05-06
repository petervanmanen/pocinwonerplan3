import dayjs from 'dayjs';
import { IBruikleen } from 'app/shared/model/bruikleen.model';
import { IStandplaats } from 'app/shared/model/standplaats.model';
import { IBelanghebbende } from 'app/shared/model/belanghebbende.model';
import { ITentoonstelling } from 'app/shared/model/tentoonstelling.model';
import { ICollectie } from 'app/shared/model/collectie.model';
import { IIncident } from 'app/shared/model/incident.model';

export interface IMuseumobject {
  id?: number;
  afmeting?: string | null;
  bezittot?: dayjs.Dayjs | null;
  bezitvanaf?: dayjs.Dayjs | null;
  medium?: string | null;
  verkrijging?: string | null;
  betreftBruikleen?: IBruikleen | null;
  locatieStandplaats?: IStandplaats | null;
  heeftBelanghebbendes?: IBelanghebbende[] | null;
  onderdeelTentoonstellings?: ITentoonstelling[] | null;
  bevatCollecties?: ICollectie[] | null;
  betreftIncidents?: IIncident[] | null;
}

export const defaultValue: Readonly<IMuseumobject> = {};
