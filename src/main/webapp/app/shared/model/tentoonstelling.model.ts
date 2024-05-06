import dayjs from 'dayjs';
import { IBruikleen } from 'app/shared/model/bruikleen.model';
import { IMuseumobject } from 'app/shared/model/museumobject.model';
import { ISamensteller } from 'app/shared/model/samensteller.model';

export interface ITentoonstelling {
  id?: number;
  datumeinde?: dayjs.Dayjs | null;
  datumstart?: dayjs.Dayjs | null;
  omschrijving?: string | null;
  subtitel?: string | null;
  titel?: string | null;
  isbedoeldvoorBruikleens?: IBruikleen[] | null;
  onderdeelMuseumobjects?: IMuseumobject[] | null;
  steltsamenSamenstellers?: ISamensteller[] | null;
}

export const defaultValue: Readonly<ITentoonstelling> = {};
