import dayjs from 'dayjs';
import { IDeelprocestype } from 'app/shared/model/deelprocestype.model';

export interface IDeelproces {
  id?: number;
  datumafgehandeld?: dayjs.Dayjs | null;
  datumgepland?: dayjs.Dayjs | null;
  isvanDeelprocestype?: IDeelprocestype;
}

export const defaultValue: Readonly<IDeelproces> = {};
