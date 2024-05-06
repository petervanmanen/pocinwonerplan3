import dayjs from 'dayjs';

export interface IAkrkadastralegemeentecode {
  id?: number;
  akrcode?: string | null;
  codeakrkadadastralegemeentecode?: string | null;
  datumbegingeldigheidakrcode?: dayjs.Dayjs | null;
  datumeindegeldigheidakrcode?: dayjs.Dayjs | null;
}

export const defaultValue: Readonly<IAkrkadastralegemeentecode> = {};
