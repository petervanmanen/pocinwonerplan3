import dayjs from 'dayjs';
import { ITentoonstelling } from 'app/shared/model/tentoonstelling.model';
import { ILener } from 'app/shared/model/lener.model';

export interface IBruikleen {
  id?: number;
  aanvraagdoor?: string | null;
  datumaanvraag?: dayjs.Dayjs | null;
  datumeinde?: dayjs.Dayjs | null;
  datumstart?: dayjs.Dayjs | null;
  toestemmingdoor?: string | null;
  isbedoeldvoorTentoonstellings?: ITentoonstelling[] | null;
  isLeners?: ILener[] | null;
}

export const defaultValue: Readonly<IBruikleen> = {};
