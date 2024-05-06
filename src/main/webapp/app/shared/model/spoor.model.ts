import dayjs from 'dayjs';
import { IVlak } from 'app/shared/model/vlak.model';

export interface ISpoor {
  id?: number;
  aard?: string | null;
  beschrijving?: string | null;
  datering?: string | null;
  datum?: dayjs.Dayjs | null;
  hoogteboven?: string | null;
  hoogteonder?: string | null;
  key?: string | null;
  keyvlak?: string | null;
  projectcd?: string | null;
  putnummer?: string | null;
  spoornummer?: string | null;
  vlaknummer?: string | null;
  vorm?: string | null;
  heeftVlak?: IVlak | null;
}

export const defaultValue: Readonly<ISpoor> = {};
