import dayjs from 'dayjs';
import { ISubsidiecomponent } from 'app/shared/model/subsidiecomponent.model';

export interface IBetaalmoment {
  id?: number;
  bedrag?: number | null;
  datum?: dayjs.Dayjs | null;
  voorschot?: boolean | null;
  heeftSubsidiecomponent?: ISubsidiecomponent;
}

export const defaultValue: Readonly<IBetaalmoment> = {
  voorschot: false,
};
