import { IMutatie } from 'app/shared/model/mutatie.model';
import { IBankafschrift } from 'app/shared/model/bankafschrift.model';

export interface IBankafschriftregel {
  id?: number;
  bedrag?: number | null;
  bij?: boolean | null;
  datum?: string | null;
  rekeningvan?: string | null;
  leidttotMutatie?: IMutatie | null;
  heeftBankafschrift?: IBankafschrift | null;
}

export const defaultValue: Readonly<IBankafschriftregel> = {
  bij: false,
};
