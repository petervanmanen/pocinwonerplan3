import dayjs from 'dayjs';
import { IVestiging } from 'app/shared/model/vestiging.model';

export interface IContact {
  id?: number;
  contactsoort?: string | null;
  datum?: dayjs.Dayjs | null;
  tekst?: string | null;
  bijVestiging?: IVestiging | null;
}

export const defaultValue: Readonly<IContact> = {};
