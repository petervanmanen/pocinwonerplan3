import { IPlan } from 'app/shared/model/plan.model';

export interface IProjectontwikkelaar {
  id?: number;
  adres?: string | null;
  naam?: string | null;
  heeftPlans?: IPlan[];
}

export const defaultValue: Readonly<IProjectontwikkelaar> = {};
