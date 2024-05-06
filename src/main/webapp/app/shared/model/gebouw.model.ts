import { IPlan } from 'app/shared/model/plan.model';

export interface IGebouw {
  id?: number;
  aantal?: string | null;
  aantaladressen?: string | null;
  aantalkamers?: string | null;
  aardgasloos?: boolean | null;
  duurzaam?: boolean | null;
  energielabel?: string | null;
  natuurinclusief?: boolean | null;
  oppervlakte?: string | null;
  regenwater?: boolean | null;
  bestaatuitPlan?: IPlan;
}

export const defaultValue: Readonly<IGebouw> = {
  aardgasloos: false,
  duurzaam: false,
  natuurinclusief: false,
  regenwater: false,
};
