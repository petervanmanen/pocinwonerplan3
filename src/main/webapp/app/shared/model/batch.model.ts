import { IApplicatie } from 'app/shared/model/applicatie.model';

export interface IBatch {
  id?: number;
  datum?: string | null;
  nummer?: string | null;
  tijd?: string | null;
  heeftherkomstApplicatie?: IApplicatie | null;
}

export const defaultValue: Readonly<IBatch> = {};
