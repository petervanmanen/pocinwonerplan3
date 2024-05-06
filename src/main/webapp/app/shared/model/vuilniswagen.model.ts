import { IContainertype } from 'app/shared/model/containertype.model';

export interface IVuilniswagen {
  id?: number;
  code?: string | null;
  kenteken?: string | null;
  type?: string | null;
  geschiktvoorContainertypes?: IContainertype[] | null;
}

export const defaultValue: Readonly<IVuilniswagen> = {};
