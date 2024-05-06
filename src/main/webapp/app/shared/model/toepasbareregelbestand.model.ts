import dayjs from 'dayjs';

export interface IToepasbareregelbestand {
  id?: number;
  datumeindegeldigheid?: dayjs.Dayjs | null;
  datumstart?: dayjs.Dayjs | null;
}

export const defaultValue: Readonly<IToepasbareregelbestand> = {};
