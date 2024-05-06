import { IActiva } from 'app/shared/model/activa.model';
import { IKostenplaats } from 'app/shared/model/kostenplaats.model';
import { IInkooporder } from 'app/shared/model/inkooporder.model';

export interface IHoofdrekening {
  id?: number;
  naam?: string | null;
  nummer?: string | null;
  omschrijving?: string | null;
  piahoofcategorieomschrijving?: string | null;
  piahoofdcategoriecode?: string | null;
  subcode?: string | null;
  subcodeomschrijving?: string | null;
  heeftActivas?: IActiva[] | null;
  heeftKostenplaats?: IKostenplaats[];
  valtbinnenHoofdrekening2?: IHoofdrekening | null;
  wordtgeschrevenopInkooporders?: IInkooporder[];
}

export const defaultValue: Readonly<IHoofdrekening> = {};
