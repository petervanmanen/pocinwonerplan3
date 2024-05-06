import dayjs from 'dayjs';
import { IApplicatie } from 'app/shared/model/applicatie.model';

export interface IVersie {
  id?: number;
  aantal?: string | null;
  datumeindesupport?: dayjs.Dayjs | null;
  kosten?: number | null;
  licentie?: string | null;
  status?: string | null;
  versienummer?: string | null;
  heeftversiesApplicatie?: IApplicatie;
}

export const defaultValue: Readonly<IVersie> = {};
