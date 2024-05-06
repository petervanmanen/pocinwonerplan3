import dayjs from 'dayjs';
import { IRechtspersoon } from 'app/shared/model/rechtspersoon.model';

export interface IVastgoedcontract {
  id?: number;
  beschrijving?: string | null;
  datumeinde?: dayjs.Dayjs | null;
  datumstart?: dayjs.Dayjs | null;
  identificatie?: string | null;
  maandbedrag?: number | null;
  opzegtermijn?: string | null;
  status?: string | null;
  type?: string | null;
  heeftRechtspersoon?: IRechtspersoon | null;
}

export const defaultValue: Readonly<IVastgoedcontract> = {};
