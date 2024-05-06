import dayjs from 'dayjs';

export interface IFormulierinhuur {
  id?: number;
  akkoordfinancieeladviseur?: string | null;
  akkoordhradviseur?: string | null;
  datuminganginhuur?: dayjs.Dayjs | null;
  functienaaminhuur?: string | null;
}

export const defaultValue: Readonly<IFormulierinhuur> = {};
