import dayjs from 'dayjs';

export interface IPgbtoekenning {
  id?: number;
  budget?: number | null;
  datumeinde?: dayjs.Dayjs | null;
  datumtoekenning?: dayjs.Dayjs | null;
}

export const defaultValue: Readonly<IPgbtoekenning> = {};
