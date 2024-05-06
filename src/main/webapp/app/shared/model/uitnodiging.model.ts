import { ILeverancier } from 'app/shared/model/leverancier.model';

export interface IUitnodiging {
  id?: number;
  afgewezen?: string | null;
  datum?: string | null;
  geaccepteerd?: string | null;
  gerichtaanLeverancier?: ILeverancier | null;
}

export const defaultValue: Readonly<IUitnodiging> = {};
