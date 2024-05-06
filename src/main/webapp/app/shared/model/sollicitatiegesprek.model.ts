import dayjs from 'dayjs';
import { ISollicitatie } from 'app/shared/model/sollicitatie.model';
import { ISollicitant } from 'app/shared/model/sollicitant.model';
import { IWerknemer } from 'app/shared/model/werknemer.model';

export interface ISollicitatiegesprek {
  id?: number;
  aangenomen?: boolean | null;
  datum?: dayjs.Dayjs | null;
  opmerkingen?: string | null;
  volgendgesprek?: boolean | null;
  inkadervanSollicitatie?: ISollicitatie | null;
  kandidaatSollicitants?: ISollicitant[] | null;
  doetsollicitatiegesprekWerknemers?: IWerknemer[] | null;
}

export const defaultValue: Readonly<ISollicitatiegesprek> = {
  aangenomen: false,
  volgendgesprek: false,
};
