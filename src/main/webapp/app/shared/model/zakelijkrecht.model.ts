import dayjs from 'dayjs';

export interface IZakelijkrecht {
  id?: number;
  datumeinde?: dayjs.Dayjs | null;
  datumstart?: dayjs.Dayjs | null;
  kosten?: number | null;
  soort?: string | null;
}

export const defaultValue: Readonly<IZakelijkrecht> = {};
