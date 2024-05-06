import { IVerblijfsobject } from 'app/shared/model/verblijfsobject.model';
import { IWijk } from 'app/shared/model/wijk.model';
import { IBelijning } from 'app/shared/model/belijning.model';
import { ISportmateriaal } from 'app/shared/model/sportmateriaal.model';

export interface IBinnenlocatie {
  id?: number;
  adres?: string | null;
  bouwjaar?: string | null;
  gemeentelijk?: boolean | null;
  geschattekostenperjaar?: number | null;
  gymzaal?: string | null;
  klokurenonderwijs?: string | null;
  klokurenverenigingen?: string | null;
  locatie?: string | null;
  onderhoudsniveau?: string | null;
  onderhoudsstatus?: string | null;
  sporthal?: string | null;
  vloeroppervlakte?: string | null;
  isgevestigdinVerblijfsobject?: IVerblijfsobject | null;
  bedientWijk?: IWijk | null;
  heeftBelijnings?: IBelijning[] | null;
  heeftSportmateriaals?: ISportmateriaal[] | null;
}

export const defaultValue: Readonly<IBinnenlocatie> = {
  gemeentelijk: false,
};
