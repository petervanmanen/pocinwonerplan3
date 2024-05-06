import { IHeffingsverordening } from 'app/shared/model/heffingsverordening.model';
import { IZaaktype } from 'app/shared/model/zaaktype.model';

export interface IHeffinggrondslag {
  id?: number;
  bedrag?: number | null;
  domein?: string | null;
  hoofdstuk?: string | null;
  omschrijving?: string | null;
  paragraaf?: string | null;
  vermeldinHeffingsverordening?: IHeffingsverordening | null;
  heeftZaaktype?: IZaaktype | null;
}

export const defaultValue: Readonly<IHeffinggrondslag> = {};
