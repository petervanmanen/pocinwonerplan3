import dayjs from 'dayjs';
import { IVastgoedobject } from 'app/shared/model/vastgoedobject.model';

export interface IInspectie {
  id?: number;
  aangemaaktdoor?: string | null;
  datumaanmaak?: dayjs.Dayjs | null;
  datumgepland?: dayjs.Dayjs | null;
  datuminspectie?: dayjs.Dayjs | null;
  datummutatie?: dayjs.Dayjs | null;
  gemuteerddoor?: string | null;
  inspectietype?: string | null;
  kenmerk?: string | null;
  omschrijving?: string | null;
  opmerkingen?: string | null;
  status?: string | null;
  betreftVastgoedobject?: IVastgoedobject | null;
}

export const defaultValue: Readonly<IInspectie> = {};
