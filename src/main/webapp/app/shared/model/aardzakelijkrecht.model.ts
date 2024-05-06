import dayjs from 'dayjs';

export interface IAardzakelijkrecht {
  id?: number;
  codeaardzakelijkrecht?: string | null;
  datumbegingeldigheidaardzakelijkrecht?: dayjs.Dayjs | null;
  datumeindegeldigheidaardzakelijkrecht?: dayjs.Dayjs | null;
  naamaardzakelijkrecht?: string | null;
}

export const defaultValue: Readonly<IAardzakelijkrecht> = {};
