import { IBelprovider } from 'app/shared/model/belprovider.model';
import { IVoertuig } from 'app/shared/model/voertuig.model';

export interface IParkeerrecht {
  id?: number;
  aanmaaktijd?: string | null;
  bedragaankoop?: number | null;
  bedragbtw?: number | null;
  datumtijdeinde?: string | null;
  datumtijdstart?: string | null;
  productnaam?: string | null;
  productomschrijving?: string | null;
  leverancierBelprovider?: IBelprovider | null;
  betreftVoertuig?: IVoertuig | null;
}

export const defaultValue: Readonly<IParkeerrecht> = {};
