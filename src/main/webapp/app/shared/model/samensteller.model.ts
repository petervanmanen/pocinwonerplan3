import { ITentoonstelling } from 'app/shared/model/tentoonstelling.model';

export interface ISamensteller {
  id?: number;
  rol?: string | null;
  steltsamenTentoonstellings?: ITentoonstelling[] | null;
}

export const defaultValue: Readonly<ISamensteller> = {};
