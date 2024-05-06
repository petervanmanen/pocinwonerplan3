import { IDoelstelling } from 'app/shared/model/doelstelling.model';
import { IProduct } from 'app/shared/model/product.model';
import { IKostenplaats } from 'app/shared/model/kostenplaats.model';
import { IHoofdrekening } from 'app/shared/model/hoofdrekening.model';
import { IHoofdstuk } from 'app/shared/model/hoofdstuk.model';
import { IBegroting } from 'app/shared/model/begroting.model';

export interface IBegrotingregel {
  id?: number;
  batenlasten?: string | null;
  bedrag?: number | null;
  soortregel?: string | null;
  betreftDoelstelling?: IDoelstelling | null;
  betreftProduct?: IProduct | null;
  betreftKostenplaats?: IKostenplaats | null;
  betreftHoofdrekening?: IHoofdrekening | null;
  betreftHoofdstuk?: IHoofdstuk | null;
  heeftBegroting?: IBegroting | null;
}

export const defaultValue: Readonly<IBegrotingregel> = {};
