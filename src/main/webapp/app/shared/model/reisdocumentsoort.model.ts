import dayjs from 'dayjs';

export interface IReisdocumentsoort {
  id?: number;
  datumbegingeldigheidreisdocumentsoort?: dayjs.Dayjs | null;
  datumeindegeldigheidreisdocumentsoort?: dayjs.Dayjs | null;
  reisdocumentcode?: string | null;
  reisdocumentomschrijving?: string | null;
}

export const defaultValue: Readonly<IReisdocumentsoort> = {};
