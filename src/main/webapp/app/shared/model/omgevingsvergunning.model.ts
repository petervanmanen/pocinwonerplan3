import { IPlan } from 'app/shared/model/plan.model';

export interface IOmgevingsvergunning {
  id?: number;
  betrekkingopPlan?: IPlan | null;
}

export const defaultValue: Readonly<IOmgevingsvergunning> = {};
