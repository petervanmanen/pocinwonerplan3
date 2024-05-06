import { IStatustype } from 'app/shared/model/statustype.model';
import { IZaak } from 'app/shared/model/zaak.model';

export interface IStatus {
  id?: number;
  datumstatusgezet?: string | null;
  indicatieiaatstgezettestatus?: string | null;
  statustoelichting?: string | null;
  isvanStatustype?: IStatustype | null;
  heeftZaak?: IZaak | null;
}

export const defaultValue: Readonly<IStatus> = {};
