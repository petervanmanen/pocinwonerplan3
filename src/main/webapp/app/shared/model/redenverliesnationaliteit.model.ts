import dayjs from 'dayjs';

export interface IRedenverliesnationaliteit {
  id?: number;
  datumaanvanggeldigheidverlies?: dayjs.Dayjs | null;
  datumeindegeldigheidverlies?: dayjs.Dayjs | null;
  omschrijvingverlies?: string | null;
  redennummerverlies?: string | null;
}

export const defaultValue: Readonly<IRedenverliesnationaliteit> = {};
