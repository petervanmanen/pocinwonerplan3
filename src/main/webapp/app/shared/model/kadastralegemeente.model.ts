import dayjs from 'dayjs';

export interface IKadastralegemeente {
  id?: number;
  datumbegingeldigheidkadastralegemeente?: dayjs.Dayjs | null;
  datumeindegeldigheidkadastralegemeente?: dayjs.Dayjs | null;
  kadastralegemeentecode?: string | null;
  naam?: string | null;
}

export const defaultValue: Readonly<IKadastralegemeente> = {};
