import dayjs from 'dayjs';
import { ILeverancier } from 'app/shared/model/leverancier.model';
import { IContract } from 'app/shared/model/contract.model';
import { IVoorziening } from 'app/shared/model/voorziening.model';

export interface ITarief {
  id?: number;
  bedrag?: number | null;
  datumeinde?: dayjs.Dayjs | null;
  datumstart?: dayjs.Dayjs | null;
  eenheid?: string | null;
  wet?: string | null;
  heeftLeverancier?: ILeverancier | null;
  bevatContract?: IContract | null;
  heeftVoorziening?: IVoorziening;
}

export const defaultValue: Readonly<ITarief> = {};
