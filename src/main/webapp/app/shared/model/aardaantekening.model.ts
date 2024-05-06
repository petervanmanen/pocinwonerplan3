import dayjs from 'dayjs';

export interface IAardaantekening {
  id?: number;
  codeaardaantekening?: string | null;
  datumbegingeldigheidaardaantekening?: dayjs.Dayjs | null;
  datumeindegeldigheidaardaantekening?: dayjs.Dayjs | null;
  naamaardaantekening?: string | null;
}

export const defaultValue: Readonly<IAardaantekening> = {};
