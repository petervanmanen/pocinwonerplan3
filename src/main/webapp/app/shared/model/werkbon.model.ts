import { IVastgoedobject } from 'app/shared/model/vastgoedobject.model';
import { IBouwdeel } from 'app/shared/model/bouwdeel.model';
import { IBouwdeelelement } from 'app/shared/model/bouwdeelelement.model';
import { IInkooporder } from 'app/shared/model/inkooporder.model';
import { ILeverancier } from 'app/shared/model/leverancier.model';

export interface IWerkbon {
  id?: number;
  betreftVastgoedobject?: IVastgoedobject | null;
  betreftBouwdeels?: IBouwdeel[] | null;
  betreftBouwdeelelements?: IBouwdeelelement[] | null;
  hoortbijInkooporder?: IInkooporder | null;
  voertwerkuitconformLeverancier?: ILeverancier | null;
}

export const defaultValue: Readonly<IWerkbon> = {};
