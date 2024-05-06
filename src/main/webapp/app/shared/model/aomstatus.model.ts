import dayjs from 'dayjs';

export interface IAomstatus {
  id?: number;
  datumbeginstatus?: dayjs.Dayjs | null;
  datumeindestatus?: dayjs.Dayjs | null;
  status?: string | null;
  statuscode?: string | null;
  statusvolgorde?: string | null;
}

export const defaultValue: Readonly<IAomstatus> = {};
