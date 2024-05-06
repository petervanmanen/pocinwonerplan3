import { IBankrekening } from 'app/shared/model/bankrekening.model';

export interface IBankafschrift {
  id?: number;
  datum?: string | null;
  nummer?: string | null;
  heeftBankrekening?: IBankrekening | null;
}

export const defaultValue: Readonly<IBankafschrift> = {};
