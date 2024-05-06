import dayjs from 'dayjs';

export interface IAutoriteitafgiftenederlandsreisdocument {
  id?: number;
  code?: string | null;
  datumbegingeldigheidautoriteitvanafgifte?: dayjs.Dayjs | null;
  datumeindegeldigheidautoriteitvanafgifte?: dayjs.Dayjs | null;
  omschrijving?: string | null;
}

export const defaultValue: Readonly<IAutoriteitafgiftenederlandsreisdocument> = {};
