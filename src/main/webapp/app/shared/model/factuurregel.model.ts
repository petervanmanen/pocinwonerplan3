import { IMutatie } from 'app/shared/model/mutatie.model';
import { IFactuur } from 'app/shared/model/factuur.model';

export interface IFactuurregel {
  id?: number;
  aantal?: string | null;
  bedragbtw?: number | null;
  bedragexbtw?: number | null;
  btwpercentage?: string | null;
  nummer?: string | null;
  omschrijving?: string | null;
  leidttotMutatie?: IMutatie | null;
  heeftFactuur?: IFactuur;
}

export const defaultValue: Readonly<IFactuurregel> = {};
