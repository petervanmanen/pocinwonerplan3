import { IKostenplaats } from 'app/shared/model/kostenplaats.model';
import { IInkooporder } from 'app/shared/model/inkooporder.model';
import { ILeverancier } from 'app/shared/model/leverancier.model';
import { IDebiteur } from 'app/shared/model/debiteur.model';

export interface IFactuur {
  id?: number;
  betaalbaarper?: string | null;
  betaaltermijn?: string | null;
  code?: string | null;
  datumfactuur?: string | null;
  factuurbedragbtw?: number | null;
  factuurbedragexclusiefbtw?: string | null;
  omschrijving?: string | null;
  schrijftopKostenplaats?: IKostenplaats | null;
  gedektviaInkooporder?: IInkooporder | null;
  crediteurLeverancier?: ILeverancier | null;
  heeftDebiteur?: IDebiteur | null;
}

export const defaultValue: Readonly<IFactuur> = {};
