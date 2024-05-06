import dayjs from 'dayjs';

export interface IMeldingeigenbijdrage {
  id?: number;
  datumstart?: dayjs.Dayjs | null;
  datumstop?: dayjs.Dayjs | null;
}

export const defaultValue: Readonly<IMeldingeigenbijdrage> = {};
