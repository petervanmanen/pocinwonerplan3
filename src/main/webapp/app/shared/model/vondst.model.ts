import dayjs from 'dayjs';
import { IVulling } from 'app/shared/model/vulling.model';

export interface IVondst {
  id?: number;
  datum?: dayjs.Dayjs | null;
  key?: string | null;
  keyvulling?: string | null;
  omschrijving?: string | null;
  omstandigheden?: string | null;
  projectcd?: string | null;
  putnummer?: string | null;
  spoornummer?: string | null;
  vlaknummer?: string | null;
  vondstnummer?: string | null;
  vullingnummer?: string | null;
  xcoordinaat?: string | null;
  ycoordinaat?: string | null;
  heeftVulling?: IVulling | null;
}

export const defaultValue: Readonly<IVondst> = {};
