import { IVuilniswagen } from 'app/shared/model/vuilniswagen.model';

export interface IRit {
  id?: number;
  eindtijd?: string | null;
  ritcode?: string | null;
  starttijd?: string | null;
  uitgevoerdmetVuilniswagen?: IVuilniswagen | null;
}

export const defaultValue: Readonly<IRit> = {};
