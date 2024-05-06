import dayjs from 'dayjs';
import { IAanbesteding } from 'app/shared/model/aanbesteding.model';
import { ILeverancier } from 'app/shared/model/leverancier.model';

export interface IKwalificatie {
  id?: number;
  eindegeldigheid?: dayjs.Dayjs | null;
  startgeldigheid?: dayjs.Dayjs | null;
  betreftAanbesteding?: IAanbesteding | null;
  heeftLeverancier?: ILeverancier | null;
}

export const defaultValue: Readonly<IKwalificatie> = {};
