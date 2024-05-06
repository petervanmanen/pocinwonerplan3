import dayjs from 'dayjs';

export interface ILstclass1 {
  id?: number;
  waarde?: number | null;
  dwhrecordid?: number | null;
  dwhodsrecordid?: number | null;
  dwhstartdt?: dayjs.Dayjs | null;
  dwheinddt?: dayjs.Dayjs | null;
  dwhrunid?: number | null;
  dwhbron?: string | null;
  dwhlaaddt?: dayjs.Dayjs | null;
  dwhactueel?: number | null;
  lstclass1id?: number | null;
}

export const defaultValue: Readonly<ILstclass1> = {};
