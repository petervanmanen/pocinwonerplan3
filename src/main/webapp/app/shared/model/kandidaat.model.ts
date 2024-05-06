import { ILeverancier } from 'app/shared/model/leverancier.model';

export interface IKandidaat {
  id?: number;
  datumingestuurd?: string | null;
  biedtaanLeverancier?: ILeverancier | null;
}

export const defaultValue: Readonly<IKandidaat> = {};
