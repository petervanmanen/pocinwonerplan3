import dayjs from 'dayjs';

export interface IWozdeelobject {
  id?: number;
  codewozdeelobject?: string | null;
  datumbegingeldigheiddeelobject?: dayjs.Dayjs | null;
  datumeindegeldigheiddeelobject?: dayjs.Dayjs | null;
  statuswozdeelobject?: string | null;
  wozdeelobjectnummer?: string | null;
}

export const defaultValue: Readonly<IWozdeelobject> = {};
